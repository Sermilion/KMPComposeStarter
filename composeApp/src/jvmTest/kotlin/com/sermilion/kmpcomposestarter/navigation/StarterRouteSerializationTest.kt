package com.sermilion.kmpcomposestarter.navigation

import androidx.navigation3.runtime.NavKey
import com.sermilion.kmpcomposestarter.common.navigation.AuthFlowRoute
import com.sermilion.kmpcomposestarter.common.navigation.MainFlowRoute
import com.sermilion.kmpcomposestarter.common.navigation.Route
import com.sermilion.kmpcomposestarter.feature.auth.navigation.LoginRoute
import com.sermilion.kmpcomposestarter.feature.auth.navigation.RegisterRoute
import com.sermilion.kmpcomposestarter.feature.home.detail.DetailRoute
import com.sermilion.kmpcomposestarter.feature.home.navigation.HomeRoute
import com.sermilion.kmpcomposestarter.feature.profile.navigation.ProfileRoute
import com.sermilion.kmpcomposestarter.feature.settings.navigation.SettingsRoute
import io.kotest.assertions.withClue
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.shouldBe
import kotlinx.serialization.json.Json
import java.io.File
import kotlin.reflect.KClass

/**
 * Sample instances of every route, for the round trip that needs real payloads.
 *
 * This list is hand-written — parameterised routes have no sample the test could derive — so the
 * first test below pins it to what `createStarterEntryProvider` actually registers. Nothing here
 * is trusted as the record of which routes exist.
 */
private val routeSamples: List<Route> =
  listOf(
    LoginRoute,
    RegisterRoute,
    HomeRoute,
    DetailRoute("x"),
    ProfileRoute,
    SettingsRoute,
  )

/**
 * Every route the features register, read out of their `*Entries.kt` sources.
 *
 * Derived rather than listed: a hand-copied second list passes happily while a feature grows a
 * route `starterSerializersModule` never learned about, which is precisely the drift that throws
 * on the first process-death restore, in production, on a screen nobody tested.
 *
 * Scanning by file-name convention is what makes a *new feature module* count too. A test that
 * read only the shell would go quiet the moment entry registration moved into the features.
 */
private val registeredRouteClasses: List<Class<*>> =
  featureEntrySources().flatMap { source ->
    val packageName =
      Regex("""^package (\S+)$""", RegexOption.MULTILINE)
        .find(source)
        ?.groupValues
        ?.get(1)
        .orEmpty()
    val importsBySimpleName =
      Regex("""^import (\S+)$""", RegexOption.MULTILINE)
        .findAll(source)
        .associateBy({ it.groupValues[1].substringAfterLast('.') }, { it.groupValues[1] })

    Regex("""\bentry<(\w+)>""")
      .findAll(source)
      .map { it.groupValues[1] }
      .map { simpleName ->
        // A route declared beside its own entries needs no import, so the file's own package is
        // the fallback before this is treated as a wiring mistake.
        val qualifiedName =
          importsBySimpleName[simpleName]
            ?: "$packageName.$simpleName".takeIf { runCatching { Class.forName(it) }.isSuccess }
            ?: error("entry<$simpleName> resolves to no import and no type in $packageName")
        Class.forName(qualifiedName)
      }.toList()
  }

/**
 * Whether [starterSerializersModule] can decode [serializedClassName] under [base].
 *
 * The cast only satisfies `getPolymorphic`'s `KClass<in T>` parameter; the lookup itself is keyed
 * by the class object passed in.
 */
@Suppress("UNCHECKED_CAST")
private fun isRegisteredUnder(
  base: KClass<*>,
  serializedClassName: String,
): Boolean =
  starterSerializersModule.getPolymorphic(
    baseClass = base as KClass<Any>,
    serializedClassName = serializedClassName,
  ) != null

/** The `*Entries.kt` file every feature module registers its routes in. */
private fun featureEntrySources(): List<String> {
  val featureRoot =
    generateSequence(File(".").absoluteFile) { it.parentFile }
      .map { File(it, "feature") }
      .firstOrNull(File::isDirectory)
      ?: error("Could not find the feature/ directory above ${File(".").absolutePath}")

  val sources =
    featureRoot
      .walkTopDown()
      .filter { it.isFile && it.name.endsWith("Entries.kt") && "/build/" !in it.invariantPath }
      .map(File::readText)
      .toList()

  check(sources.isNotEmpty()) { "No feature *Entries.kt sources found under $featureRoot" }
  return sources
}

private val File.invariantPath: String get() = absolutePath.replace(File.separatorChar, '/')

class StarterRouteSerializationTest :
  FunSpec({

    val json = Json { serializersModule = starterSerializersModule }

    test("every route the entry provider registers is registered in the serializers module") {
      registeredRouteClasses.shouldNotBeEmpty()

      registeredRouteClasses.forEach { routeClass ->
        listOf(NavKey::class, Route::class, MainFlowRoute::class, AuthFlowRoute::class)
          .filter { base -> base.java.isAssignableFrom(routeClass) }
          .forEach { base ->
            withClue("${routeClass.simpleName} under ${base.simpleName}") {
              isRegisteredUnder(base, routeClass.name) shouldBe true
            }
          }
      }
    }

    test("the round-trip samples cover every route the entry provider registers") {
      routeSamples.map { it::class.java }.toSet() shouldBe registeredRouteClasses.toSet()
    }

    test("every route the entry provider registers round-trips through the serializers module") {
      routeSamples.forEach { route ->
        withClue(route::class.simpleName.orEmpty()) {
          json.decodeFromString<Route>(json.encodeToString<Route>(route)) shouldBe route
        }
      }
    }
  })
