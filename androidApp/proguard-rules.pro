# R8 rules for the release build.
#
# Most of this app's dependencies ship their own consumer rules — Compose, OkHttp, Coil, Room and
# kotlinx-serialization-core all do — so this file only covers what those cannot see: code this
# template reaches reflectively, and the two libraries whose optional dependencies R8 warns about.
#
# Keep it this short. Every broad `-keep` here is minification the template silently gives up, and
# a fork inherits that. Verify a change with `./gradlew :androidApp:assembleRelease`.

# --- kotlinx.serialization ------------------------------------------------------------------
# The generated `$serializer` and its `Companion` are reached through the synthetic
# `Companion.serializer()` accessor, which R8 cannot trace from a `@Serializable` annotation alone.
# This covers the navigation routes, `UserPreferences` and every data-layer model.
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.**

-if @kotlinx.serialization.Serializable class **
-keepclassmembers class <1> {
  static <1>$Companion Companion;
}
-if @kotlinx.serialization.Serializable class ** {
  static **$* *;
}
-keepclassmembers class <2>$<3> {
  kotlinx.serialization.KSerializer serializer(...);
}
-if @kotlinx.serialization.Serializable class **
-keepclassmembers class <1> {
  *** Companion;
  kotlinx.serialization.KSerializer serializer(...);
}

# `data object` routes are compared by identity through the polymorphic registry, so their
# INSTANCE field has to survive.
-keepclassmembers @kotlinx.serialization.Serializable class ** {
  public static ** INSTANCE;
}

# --- Room 3 ---------------------------------------------------------------------------------
# `@ConstructedBy(UserDatabaseConstructor::class)` resolves the generated constructor object by
# name at runtime rather than through a direct call site.
-keep class com.sermilion.kmpcomposestarter.core.data.db.UserDatabaseConstructor { *; }
-keep class * extends androidx.room3.RoomDatabase { <init>(); }

# --- Ktor -----------------------------------------------------------------------------------
# Ktor references optional engines and slf4j bindings this app does not bundle; the engine is
# named concretely in `StarterHttpClientEngine.android.kt`, so the warnings are noise.
-dontwarn io.ktor.**
-dontwarn org.slf4j.**

# --- Coroutines -----------------------------------------------------------------------------
-dontwarn kotlinx.coroutines.**
-keepclassmembers class kotlinx.coroutines.** {
  volatile <fields>;
}
