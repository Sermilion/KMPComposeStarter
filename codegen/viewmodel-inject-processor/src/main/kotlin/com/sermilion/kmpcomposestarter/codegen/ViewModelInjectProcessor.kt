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

class ViewModelInjectProcessor(
  private val codeGenerator: CodeGenerator,
  private val logger: KSPLogger,
) : SymbolProcessor {

  private companion object {
    const val SINGLE_IN_ANNOTATION = "software.amazon.lastmile.kotlin.inject.anvil.SingleIn"
    const val VIEW_MODEL_CLASS = "androidx.lifecycle.ViewModel"
    const val SAVED_STATE_HANDLE_CLASS = "androidx.lifecycle.SavedStateHandle"
  }

  override fun process(resolver: Resolver): List<KSAnnotated> {
    val singleInSymbols = resolver
      .getSymbolsWithAnnotation(SINGLE_IN_ANNOTATION)
      .filterIsInstance<KSClassDeclaration>()
      .filter { classDeclaration ->
        classDeclaration.superTypes.any { superType ->
          val declaration = superType.resolve().declaration
          declaration.qualifiedName?.asString() == VIEW_MODEL_CLASS ||
            isSubtypeOfViewModel(declaration, resolver)
        }
      }

    val assistedViewModels = resolver
      .getSymbolsWithAnnotation("me.tatarka.inject.annotations.Assisted")
      .filterIsInstance<KSValueParameter>()
      .mapNotNull { parameter ->
        val constructor = parameter.parent
        constructor?.parent as? KSClassDeclaration
      }
      .distinct()
      .filter { classDeclaration ->
        classDeclaration.superTypes.any { superType ->
          val declaration = superType.resolve().declaration
          declaration.qualifiedName?.asString() == VIEW_MODEL_CLASS ||
            isSubtypeOfViewModel(declaration, resolver)
        }
      }

    val symbols = (singleInSymbols + assistedViewModels).distinct()

    if (!symbols.iterator().hasNext()) return emptyList()

    symbols.forEach { classDeclaration ->
      if (!classDeclaration.validate()) {
        return@forEach
      }

      try {
        generateViewModelEntry(classDeclaration, resolver)
      } catch (e: Exception) {
        logger.error(
          "Failed to generate entry for ${classDeclaration.simpleName.asString()}: ${e.message}",
          classDeclaration,
        )
        e.printStackTrace()
      }
    }

    return emptyList()
  }

  private fun generateViewModelEntry(classDeclaration: KSClassDeclaration, resolver: Resolver) {
    val viewModelClassName = classDeclaration.toClassName()
    val packageName = viewModelClassName.packageName
    val viewModelSimpleName = viewModelClassName.simpleName
    val entryName = "${viewModelSimpleName}_Entry"

    val extendsViewModel = classDeclaration.superTypes.any { superType ->
      val declaration = superType.resolve().declaration
      declaration.qualifiedName?.asString() == VIEW_MODEL_CLASS ||
        isSubtypeOfViewModel(declaration, resolver)
    }

    if (!extendsViewModel) {
      logger.error(
        "Can only be applied to classes extending androidx.lifecycle.ViewModel. " +
          "$viewModelSimpleName does not extend ViewModel.",
        classDeclaration,
      )
      return
    }

    logger.info("Generating entry for $viewModelSimpleName")

    val constructor = classDeclaration.primaryConstructor
      ?: throw IllegalStateException("No primary constructor found for $viewModelSimpleName")

    val parameters = constructor.parameters

    val assistedParams = parameters.filter { param ->
      val hasAssistedAnnotation = param.annotations.any { it.shortName.asString() == "Assisted" }
      val isSavedStateHandle =
        param.type.resolve().declaration.qualifiedName?.asString() == SAVED_STATE_HANDLE_CLASS
      hasAssistedAnnotation || isSavedStateHandle
    }

    logger.info(
      "Assisted params for $viewModelSimpleName: ${assistedParams.map {
        it.name?.asString()
      }}",
    )

    val inject = ClassName("me.tatarka.inject.annotations", "Inject")
    val contributesBinding =
      ClassName("software.amazon.lastmile.kotlin.inject.anvil", "ContributesBinding")

    val scopeClassName = classDeclaration.annotations.firstOrNull { annotation ->
      annotation.shortName.asString() == "SingleIn"
    }?.arguments?.firstOrNull()?.value?.let { scopeArg ->
      val scopeType = scopeArg as? KSType
      val scopeValue = scopeType?.declaration?.qualifiedName?.asString()
      when (scopeValue) {
        "com.sermilion.kmpcomposestarter.common.di.AppScope",
        "software.amazon.lastmile.kotlin.inject.anvil.AppScope",
        ->
          ClassName("software.amazon.lastmile.kotlin.inject.anvil", "AppScope")
        "com.sermilion.kmpcomposestarter.common.di.UserScope" ->
          ClassName("com.sermilion.kmpcomposestarter.common.di", "UserScope")
        "com.sermilion.kmpcomposestarter.common.di.ScreenScope" ->
          ClassName("com.sermilion.kmpcomposestarter.common.di", "ScreenScope")
        else -> {
          logger.warn("Unknown scope $scopeValue, defaulting to ScreenScope", classDeclaration)
          ClassName("com.sermilion.kmpcomposestarter.common.di", "ScreenScope")
        }
      }
    } ?: ClassName("com.sermilion.kmpcomposestarter.common.di", "ScreenScope")

    val viewModelEntry = ClassName("com.sermilion.kmpcomposestarter.common.di", "ViewModelEntry")
    val viewModelClass = ClassName("androidx.lifecycle", "ViewModel")
    val assistedArgs = ClassName("com.sermilion.kmpcomposestarter.common.di", "AssistedArgs")

    val functionType = if (assistedParams.isNotEmpty()) {
      val paramTypes = assistedParams.map { it.type.toTypeName() }.toTypedArray()
      LambdaTypeName.get(
        parameters = paramTypes,
        returnType = viewModelClassName,
      )
    } else {
      LambdaTypeName.get(returnType = viewModelClassName)
    }

    val entryConstructorParam = ParameterSpec.builder("create", functionType).build()

    val createMethod = if (assistedParams.isNotEmpty()) {
      FunSpec.builder("create")
        .addModifiers(KModifier.OVERRIDE)
        .addParameter("args", assistedArgs)
        .returns(viewModelClass)
        .apply {
          val validParams = assistedParams.mapNotNull { param ->
            param.name?.asString()?.let { name -> name to param.type.toTypeName() }
          }
          validParams.forEach { (paramName, paramType) ->
            addStatement(
              "val %L: %T = args[%S] ?: error(%S)",
              paramName,
              paramType,
              paramName,
              "Missing assisted arg '$paramName' for $viewModelSimpleName",
            )
          }
          addStatement(
            "return create(%L)",
            validParams.joinToString(", ") { it.first },
          )
        }
        .build()
    } else {
      FunSpec.builder("create")
        .addModifiers(KModifier.OVERRIDE)
        .addParameter("args", assistedArgs)
        .returns(viewModelClass)
        .addStatement("return create()")
        .build()
    }

    val entryClass = TypeSpec.classBuilder(entryName)
      .addAnnotation(inject)
      .addAnnotation(
        AnnotationSpec.builder(contributesBinding)
          .addMember("%T::class", scopeClassName)
          .addMember("multibinding = true")
          .build(),
      )
      .addSuperinterface(viewModelEntry)
      .primaryConstructor(
        FunSpec.constructorBuilder()
          .addParameter(entryConstructorParam)
          .build(),
      )
      .addProperty(
        PropertySpec.builder("create", functionType, KModifier.PRIVATE)
          .initializer("create")
          .build(),
      )
      .addProperty(
        PropertySpec.builder(
          "kclass",
          ClassName(
            "kotlin.reflect",
            "KClass",
          ).parameterizedBy(WildcardTypeName.producerOf(viewModelClass)),
        )
          .addModifiers(KModifier.OVERRIDE)
          .initializer("%T::class", viewModelClassName)
          .build(),
      )
      .addFunction(createMethod)
      .build()

    val fileSpec = FileSpec.builder(packageName, entryName)
      .addType(entryClass)
      .build()

    fileSpec.writeTo(codeGenerator, Dependencies(true, classDeclaration.containingFile!!))
  }

  private fun isSubtypeOfViewModel(declaration: KSDeclaration, resolver: Resolver): Boolean {
    if (declaration.qualifiedName?.asString() == VIEW_MODEL_CLASS) {
      return true
    }
    if (declaration is KSClassDeclaration) {
      return declaration.superTypes.any { superType ->
        isSubtypeOfViewModel(superType.resolve().declaration, resolver)
      }
    }
    return false
  }
}
