package com.sermilion.kmpcomposestarter.codegen

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.google.devtools.ksp.symbol.KSValueParameter
import com.google.devtools.ksp.validate
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.LambdaTypeName
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.WildcardTypeName
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toTypeName
import com.squareup.kotlinpoet.ksp.writeTo

/**
 * Generates one `ViewModelEntry` multibinding per class annotated with `@ContributesViewModel`.
 *
 * Every project-specific DI type is resolved from [diPackage] (the `di.package` KSP option), so
 * the processor carries no hardcoded package of its own.
 */
class ViewModelInjectProcessor(
  private val codeGenerator: CodeGenerator,
  private val logger: KSPLogger,
  private val diPackage: String,
) : SymbolProcessor {
  private companion object {
    const val VIEW_MODEL_CLASS = "androidx.lifecycle.ViewModel"
    const val SAVED_STATE_HANDLE_CLASS = "androidx.lifecycle.SavedStateHandle"
    const val ASSISTED_ANNOTATION = "me.tatarka.inject.annotations.Assisted"
    const val ANVIL_PACKAGE = "software.amazon.lastmile.kotlin.inject.anvil"
    const val APP_SCOPE_CLASS = "$ANVIL_PACKAGE.AppScope"
  }

  private val contributesViewModelAnnotation = "$diPackage.ContributesViewModel"
  private val viewModelEntryClass = ClassName(diPackage, "ViewModelEntry")
  private val assistedArgsClass = ClassName(diPackage, "AssistedArgs")
  private val viewModelClass = ClassName("androidx.lifecycle", "ViewModel")
  private val injectClass = ClassName("me.tatarka.inject.annotations", "Inject")
  private val contributesBindingClass = ClassName(ANVIL_PACKAGE, "ContributesBinding")

  private val scopesByQualifiedName =
    mapOf(
      APP_SCOPE_CLASS to ClassName(ANVIL_PACKAGE, "AppScope"),
      "$diPackage.UserScope" to ClassName(diPackage, "UserScope"),
      "$diPackage.ScreenScope" to ClassName(diPackage, "ScreenScope"),
    )

  override fun process(resolver: Resolver): List<KSAnnotated> {
    val symbols =
      resolver
        .getSymbolsWithAnnotation(contributesViewModelAnnotation)
        .filterIsInstance<KSClassDeclaration>()
        .toList()

    val (resolved, deferred) = symbols.partition { it.validate() }

    resolved.forEach(::generateViewModelEntry)

    return deferred
  }

  private fun generateViewModelEntry(classDeclaration: KSClassDeclaration) {
    val viewModelClassName = classDeclaration.toClassName()
    val viewModelSimpleName = viewModelClassName.simpleName

    val scopeClassName = resolveEntryScope(classDeclaration, viewModelSimpleName) ?: return
    val assistedParams = resolveAssistedParams(classDeclaration, viewModelSimpleName) ?: return

    writeEntryFile(classDeclaration, viewModelClassName, scopeClassName, assistedParams)
  }

  private fun resolveEntryScope(
    classDeclaration: KSClassDeclaration,
    viewModelSimpleName: String,
  ): ClassName? {
    if (!isSubtypeOfViewModel(classDeclaration)) {
      logger.error(
        "@ContributesViewModel can only be applied to classes extending $VIEW_MODEL_CLASS. " +
          "$viewModelSimpleName does not extend ViewModel.",
        classDeclaration,
      )
      return null
    }
    return resolveScope(classDeclaration)
  }

  /**
   * The named `@Assisted` constructor parameters, or null once the first problem has been reported.
   * Reporting stops at the first problem so a single mistake produces a single message.
   */
  private fun resolveAssistedParams(
    classDeclaration: KSClassDeclaration,
    viewModelSimpleName: String,
  ): List<Pair<String, KSValueParameter>>? {
    val parameters = classDeclaration.primaryConstructor?.parameters
    val assistedParams = parameters.orEmpty().filter(::isAssisted)
    val namedAssistedParams =
      assistedParams.mapNotNull { parameter ->
        parameter.name?.asString()?.let { name -> name to parameter }
      }

    val error =
      assistedParamsError(
        parameters = parameters,
        viewModelSimpleName = viewModelSimpleName,
        assistedCount = assistedParams.size,
        namedCount = namedAssistedParams.size,
      )
    if (error != null) {
      logger.error(error, classDeclaration)
      return null
    }
    return namedAssistedParams
  }

  private fun assistedParamsError(
    parameters: List<KSValueParameter>?,
    viewModelSimpleName: String,
    assistedCount: Int,
    namedCount: Int,
  ): String? {
    val unmarkedSavedStateHandle =
      parameters?.firstOrNull { parameter ->
        !isAssisted(parameter) && qualifiedTypeName(parameter) == SAVED_STATE_HANDLE_CLASS
      }
    return when {
      parameters == null -> "No primary constructor found for $viewModelSimpleName"
      unmarkedSavedStateHandle != null ->
        "SavedStateHandle parameter '${unmarkedSavedStateHandle.name?.asString()}' of " +
          "$viewModelSimpleName must be annotated @Assisted."
      namedCount != assistedCount ->
        "Every @Assisted parameter of $viewModelSimpleName must have a name."
      else -> null
    }
  }

  private fun writeEntryFile(
    classDeclaration: KSClassDeclaration,
    viewModelClassName: ClassName,
    scopeClassName: ClassName,
    assistedParams: List<Pair<String, KSValueParameter>>,
  ) {
    val entryName = "${viewModelClassName.simpleName}_Entry"
    val fileSpec =
      FileSpec
        .builder(viewModelClassName.packageName, entryName)
        .addType(
          buildEntryType(
            entryName = entryName,
            viewModelClassName = viewModelClassName,
            scopeClassName = scopeClassName,
            assistedParams = assistedParams,
          ),
        ).build()

    val containingFile = classDeclaration.containingFile
    val dependencies =
      if (containingFile == null) {
        Dependencies(aggregating = false)
      } else {
        Dependencies(aggregating = false, containingFile)
      }
    fileSpec.writeTo(codeGenerator, dependencies)
  }

  private fun buildEntryType(
    entryName: String,
    viewModelClassName: ClassName,
    scopeClassName: ClassName,
    assistedParams: List<Pair<String, KSValueParameter>>,
  ): TypeSpec {
    val assistedTypes =
      assistedParams
        .map { (_, parameter) -> parameter.type.toTypeName() }
        .toTypedArray()
    val functionType =
      LambdaTypeName.get(
        parameters = assistedTypes,
        returnType = viewModelClassName,
      )
    val savedStateHandleArgName =
      assistedParams
        .firstOrNull { (_, parameter) -> qualifiedTypeName(parameter) == SAVED_STATE_HANDLE_CLASS }
        ?.first

    return TypeSpec
      .classBuilder(entryName)
      .addAnnotation(injectClass)
      .addAnnotation(
        AnnotationSpec
          .builder(contributesBindingClass)
          .addMember("%T::class", scopeClassName)
          .addMember("multibinding = true")
          .build(),
      ).addSuperinterface(viewModelEntryClass)
      .primaryConstructor(
        FunSpec
          .constructorBuilder()
          .addParameter(ParameterSpec.builder("create", functionType).build())
          .build(),
      ).addProperty(
        PropertySpec
          .builder("create", functionType, KModifier.PRIVATE)
          .initializer("create")
          .build(),
      ).addProperty(
        PropertySpec
          .builder(
            "kclass",
            ClassName("kotlin.reflect", "KClass")
              .parameterizedBy(WildcardTypeName.producerOf(viewModelClass)),
          ).addModifiers(KModifier.OVERRIDE)
          .initializer("%T::class", viewModelClassName)
          .build(),
      ).addProperty(
        PropertySpec
          .builder("savedStateHandleArgName", STRING_NULLABLE)
          .addModifiers(KModifier.OVERRIDE)
          .apply {
            if (savedStateHandleArgName == null) {
              initializer("null")
            } else {
              initializer("%S", savedStateHandleArgName)
            }
          }.build(),
      ).addFunction(
        buildCreateFunction(viewModelClassName.simpleName, assistedParams),
      ).build()
  }

  private fun buildCreateFunction(
    viewModelSimpleName: String,
    assistedParams: List<Pair<String, KSValueParameter>>,
  ): FunSpec {
    val builder =
      FunSpec
        .builder("create")
        .addModifiers(KModifier.OVERRIDE)
        .addParameter("args", assistedArgsClass)
        .returns(viewModelClass)

    if (assistedParams.isEmpty()) {
      return builder.addStatement("return create()").build()
    }

    assistedParams.forEach { (name, parameter) ->
      builder.addStatement(
        "val %L: %T = args[%S] ?: error(%S)",
        name,
        parameter.type.toTypeName(),
        name,
        "Missing assisted arg '$name' for $viewModelSimpleName",
      )
    }
    return builder
      .addStatement("return create(%L)", assistedParams.joinToString(", ") { it.first })
      .build()
  }

  private fun resolveScope(classDeclaration: KSClassDeclaration): ClassName? {
    val scopeArgument =
      classDeclaration.annotations
        .firstOrNull { it.shortName.asString() == "ContributesViewModel" }
        ?.arguments
        ?.firstOrNull()
        ?.value as? KSType
    val scopeQualifiedName = scopeArgument?.declaration?.qualifiedName?.asString()

    return scopesByQualifiedName[scopeQualifiedName] ?: run {
      logger.error(
        "Unknown ViewModel scope '$scopeQualifiedName'. Supported scopes: " +
          scopesByQualifiedName.keys.joinToString(),
        classDeclaration,
      )
      null
    }
  }

  private fun isAssisted(parameter: KSValueParameter): Boolean =
    parameter.annotations.any { annotation ->
      annotation.annotationType
        .resolve()
        .declaration.qualifiedName
        ?.asString() ==
        ASSISTED_ANNOTATION
    }

  private fun qualifiedTypeName(parameter: KSValueParameter): String? =
    parameter.type
      .resolve()
      .declaration.qualifiedName
      ?.asString()

  private fun isSubtypeOfViewModel(declaration: KSDeclaration): Boolean {
    if (declaration.qualifiedName?.asString() == VIEW_MODEL_CLASS) return true
    if (declaration !is KSClassDeclaration) return false
    return declaration.superTypes.any { superType ->
      isSubtypeOfViewModel(superType.resolve().declaration)
    }
  }
}

private val STRING_NULLABLE = ClassName("kotlin", "String").copy(nullable = true)
