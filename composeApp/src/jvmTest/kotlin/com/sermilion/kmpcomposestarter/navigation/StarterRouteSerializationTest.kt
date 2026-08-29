package com.sermilion.kmpcomposestarter.navigation

import androidx.navigation3.runtime.NavKey
import com.sermilion.kmpcomposestarter.common.navigation.AuthFlowRoute
import com.sermilion.kmpcomposestarter.common.navigation.Route
import com.sermilion.kmpcomposestarter.common.navigation.TopLevelRoute
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
import java.io.File
import kotlin.reflect.KClass
import kotlinx.serialization.json.Json

/**
 * Sample instances of every route, for the round trip that needs real payloads.
 *
 * This list is hand-written — parameterised routes have no sample the test could derive — so the
 * first test below pins it to what `createStarterEntryProvider` actually registers. Nothing here
 * is trusted as the record of which routes exist.
 */
private val routeSamples: List<Route> = listOf(
  LoginRoute,
  RegisterRoute,
  HomeRoute,
  DetailRoute("x"),
  ProfileRoute,
  SettingsRoute,
)

/**
 * The routes `createStarterEntryProvider` registers, read out of its source.
 *
 * Derived rather than listed: a hand-copied second list passes happily while the entry provider
 * grows a route `starterSerializersModule` never learned about, which is precisely the drift that
 * throws on the first process-death restore, in production, on a screen nobody tested.
 */
private val registeredRouteClasses: List<Class<*>> = run {
  val source = entryProviderSource()
  val importsBySimpleName = Regex("""^import (\S+)$""", RegexOption.MULTILINE)
    .findAll(source)
    .associateBy({ it.groupValues[1].substringAfterLast('.') }, { it.groupValues[1] })
  Regex("""\bentry<(\w+)>""").findAll(source)
    .map { it.groupValues[1] }
    .map { simpleName ->
      val qualifiedName = importsBySimpleName[simpleName]
        ?: error("entry<$simpleName> in StarterEntryProvider.kt has no matching import")
      Class.forName(qualifiedName)
    }
    .toList()
}

/**
 * Whether [starterSerializersModule] can decode [serializedClassName] under [base].
 *
 * The cast only satisfies `getPolymorphic`'s `KClass<in T>` parameter; the lookup itself is keyed
 * by the class object passed in.
 */
@Suppress("UNCHECKED_CAST")
private fun isRegisteredUnder(base: KClass<*>, serializedClassName: String): Boolean =
  starterSerializersModule.getPolymorphic(
    baseClass = base as KClass<Any>,
    serializedClassName = serializedClassName,
  ) != null

private fun entryProviderSource(): String {
  val relativePath = "composeApp/src/commonMain/kotlin/com/sermilion/kmpcomposestarter/" +
    "navigation/StarterEntryProvider.kt"
  val file = generateSequence(File(".").absoluteFile) { it.parentFile }
    .map { File(it, relativePath) }
    .firstOrNull(File::isFile)
    ?: error("Could not find $relativePath above ${File(".").absolutePath}")
  return file.readText()
}

class StarterRouteSerializationTest :
  FunSpec({

    val json = Json { serializersModule = starterSerializersModule }

    test("every route the entry provider registers is registered in the serializers module") {
      // A regex that stopped matching would otherwise turn this whole test green on an empty set.
      registeredRouteClasses.shouldNotBeEmpty()

      registeredRouteClasses.forEach { routeClass ->
        // Each base the route implements is its own polymorphic block, and the saver decodes
        // stacks as AuthFlowRoute and TopLevelRoute — registering only under Route still throws
        // on restore.
        listOf(NavKey::class, Route::class, TopLevelRoute::class, AuthFlowRoute::class)
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
