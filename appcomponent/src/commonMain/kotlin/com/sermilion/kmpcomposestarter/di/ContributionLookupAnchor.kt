package com.sermilion.kmpcomposestarter.di

import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesTo

/**
 * Anchors kotlin-inject-anvil's contribution lookup package inside this module.
 *
 * `@MergeComponent` gathers contributions by asking KSP for every declaration in anvil's
 * `amazon.lastmile.inject` lookup package. On Kotlin/Native that resolves through the Analysis
 * API's package scope, which reports the package as absent — and so yields nothing at all,
 * dependency klibs included — unless the module being processed already declares something in it.
 * JVM and Android read the package straight off the compile classpath and never need the nudge.
 *
 * `composeApp` contributes nothing of its own, so on the iOS targets `IosApplicationComponent`
 * merged to an empty interface and every binding coming from `core:*` and `feature:*` was reported
 * as a missing provider, failing `kspKotlinIosArm64` and `kspKotlinIosSimulatorArm64`. Contributing
 * one empty interface makes the package exist during composeApp's own KSP round, after which the
 * dependency contributions resolve; it adds an empty supertype to the merged component and nothing
 * else.
 *
 * It lives in `commonMain` rather than `iosMain` so a target added later cannot silently
 * rediscover this. Drop it once KSP enumerates klib packages unprompted, and confirm with
 * `./gradlew :composeApp:linkDebugFrameworkIosArm64` before deleting.
 */
@ContributesTo(AppScope::class)
interface ContributionLookupAnchor
