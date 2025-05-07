@file:OptIn(KspExperimental::class)

import com.android.build.api.dsl.VariantDimension
import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import com.google.devtools.ksp.KspExperimental
import com.google.firebase.crashlytics.buildtools.gradle.CrashlyticsExtension
import com.mikepenz.aboutlibraries.plugin.DuplicateMode
import com.mikepenz.aboutlibraries.plugin.DuplicateRule
import com.mikepenz.aboutlibraries.plugin.StrictMode
import java.io.IOException
import java.util.Properties
import org.gradle.kotlin.dsl.implementation
import org.jetbrains.dokka.gradle.engine.parameters.VisibilityModifier
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeFeatureFlag
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    alias(libs.plugins.aboutLibraries)
    alias(libs.plugins.android.application)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.dependencyAnalysis)
    alias(libs.plugins.dokka)
    alias(libs.plugins.firebaseCrashlyticsPlugin)
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.protobuf)
    alias(libs.plugins.versions)
    alias(libs.plugins.firebasePerfPlugin)
    alias(libs.plugins.sortDependencies)
    id("shared-build-conventions")

    kotlin("plugin.power-assert") version "2.1.20"
}

val keystoreProperties: Properties = Properties()
var successfulLoadProperties: Boolean = false
try {
    rootProject.file("keystore.properties").inputStream().use { it ->
        keystoreProperties.load(it)
    }
    successfulLoadProperties = true
} catch (_: IOException) {
}

/**
 * Type safety for buildConfigField
 */
private inline fun <reified ValueT> VariantDimension.buildConfigField(name: String, value: ValueT) {
    val resolvedValue: String = when (value) {
        is String -> "\"$value\""
        is Boolean -> "Boolean.parseBoolean(\"$value\")"
        else -> value.toString()
    }

    buildConfigField(ValueT::class.java.simpleName, name, resolvedValue)
}

android {
    namespace = "com.brokenkernel.improvtools"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.brokenkernel.improvtools"
        minSdk = 26
        targetSdk = 36
        versionCode = 197
        versionName = "0.0.$versionCode"

        testInstrumentationRunner = "com.brokenkernel.improvtools.infrastructure.ImprovToolsTestRunner"
        proguardFiles(
            getDefaultProguardFile("proguard-android-optimize.txt"),
            "proguard-rules.pro",
        )
    }

    signingConfigs {
        if (successfulLoadProperties) {
            create("config") {
                keyAlias = keystoreProperties["keyAlias"] as String
                keyPassword = keystoreProperties["keyPassword"] as String
                storeFile = file(keystoreProperties["storeFile"] as String)
                storePassword = keystoreProperties["storePassword"] as String
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            if (successfulLoadProperties) {
                signingConfig = signingConfigs.getByName("config")
            }
            ndk {
                debugSymbolLevel = "FULL" // SYMBOL_TABLE - if it gets too big
            }
            buildConfigField("ENABLE_STRICT_MODE_DEATH", false)
            buildConfigField("ENABLE_CRASHLYTICS", true)
            configure<CrashlyticsExtension> {
                nativeSymbolUploadEnabled = true
                mappingFileUploadEnabled = true
            }
        }

        debug {
            buildConfigField("ENABLE_STRICT_MODE_DEATH", true)
            buildConfigField("ENABLE_CRASHLYTICS", false)
            isPseudoLocalesEnabled = true
            configure<CrashlyticsExtension> {
                nativeSymbolUploadEnabled = true
                mappingFileUploadEnabled = true
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeCompiler {
        includeSourceInformation = true
        includeTraceMarkers = true
        featureFlags = setOf(
            ComposeFeatureFlag.OptimizeNonSkippingGroups,
            ComposeFeatureFlag.PausableComposition,
        )
    }
    testOptions {
        managedDevices {
            localDevices {
                create("Pixel4Managed") {
                    device = "Pixel 4"
                    apiLevel = 35
                    systemImageSource = "google-atd"
                }
                create("Nexus9Managed") {
                    device = "Nexus9"
                    apiLevel = 35
                    systemImageSource = "google-atd"
                }
            }
            groups {
                create("phoneAndTablet") {
                    targetDevices.add(allDevices["Pixel4Managed"])
                    targetDevices.add(allDevices["Nexus9Managed"])
                }
            }
        }
        unitTests {
            isIncludeAndroidResources = true
        }
        emulatorControl {
            enable = true
        }
    }
    buildToolsVersion = "35.0.0"
    dependenciesInfo {
        includeInApk = true
        includeInBundle = true
    }
    lint {
        lintConfig = file("lint.xml")
        baseline = file("lint-baseline.xml")
        checkDependencies = true
        warningsAsErrors = true
    }
    packaging {
        jniLibs {
            // unable to strip, so shut up warnings
            keepDebugSymbols.add("**/libandroidx.graphics.path.so")
            keepDebugSymbols.add("**/libdatastore_shared_counter.so")
            keepDebugSymbols.add("**/libgraphics-core.so")
            keepDebugSymbols.add("**/libink.so")
        }
    }
    androidResources {
        generateLocaleConfig = true
    }

    // https://kotlinlang.org/docs/whatsnew-eap.html#leave-feedback
//    kotlinOptions {
//        freeCompilerArgs += listOf(
//            "-Werror",
//            "-Wextra",
//        )
//    }
}

dependencies {
    implementation(enforcedPlatform(libs.androidx.compose.bom)) {
        because("we are an android compose application")
    }
    implementation(enforcedPlatform(libs.com.fasterxml.jackson.bom))
    implementation(enforcedPlatform(libs.com.google.firebase.firebaseBom))
    implementation(enforcedPlatform(libs.kotlin.bom))
    implementation(project(":components"))
    implementation(libs.aboutlibraries.compose.m3)
    implementation(libs.aboutlibraries.core)
    implementation(libs.accompanist.permissions)
    implementation(libs.androidbrowserhelper)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.annotation)
    implementation(libs.androidx.appsearch)
    implementation(libs.androidx.appsearch.platform.storage)
    implementation(libs.androidx.browser)
    implementation(libs.androidx.compose.material.materialIconsExtended)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.datastore)
    implementation(libs.androidx.datastore.core)
    //    implementation(libs.androidx.glance.appwidget)
    //    implementation(libs.androidx.glance.material3)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.lifecycle.process)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.service)
    implementation(libs.androidx.lifecycle.viewModel.compose)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.savedstate)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material3.adaptive)
    implementation(libs.androidx.material3.adaptive.layout)
    implementation(libs.androidx.material3.adaptive.navigation)
    implementation(libs.androidx.metrics.performance)
    implementation(libs.androidx.navigation.navigationCompose)
    implementation(libs.androidx.profileinstaller)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.paging)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.runtime.tracing)
    implementation(libs.androidx.startup.runtime)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.window)
    implementation(libs.androidx.work.multiprocess)
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.bundles.firebase)
    implementation(libs.com.google.protobuf.protobufJavalite)
    implementation(libs.extjwnl)
    implementation(libs.guava)
    implementation(libs.hilt.android)
    implementation(libs.javax.inject)
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinx.collections.immutable.jvm)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.serialization.protobuf)
    implementation(libs.net.engawapg.lib.zoomable)
    implementation(libs.play.services.instantapps)
    implementation(libs.play.services.oss.licenses)
    implementation(libs.reorderable)

    debugImplementation(libs.androidx.ui.tooling)

    // TODO: add profilable build
    // TODO: add leakCanary build
    //    debugImplementation(libs.leakcanary.android)
    runtimeOnly(libs.extjwnl.data.wn31)
    runtimeOnly(libs.slf4j.android)

    debugRuntimeOnly(libs.androidx.ui.test.manifest)

    testImplementation(kotlin("test"))
    testImplementation(libs.androidx.lifecycle.runtime.testing)
    testImplementation(libs.androidx.room.testing)
    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.activity)
    androidTestImplementation(libs.androidx.datastore)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.espresso.device)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.navigation.navigationTesting)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation(libs.androidx.work.testing)
    androidTestImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.tools.fastlane.screengrab)

    dokkaPlugin(libs.android.documentation.plugin)
    dokkaPlugin(libs.mathjax.plugin)

    ksp(libs.androidx.appsearch.compiler)
    ksp(libs.androidx.lifecycle.compiler)
    // If this project uses any Kotlin source, use Kotlin Symbol Processing (KSP)
    // See Add the KSP plugin to your project
    ksp(libs.androidx.room.compiler)
    ksp(libs.hilt.compiler)

    ktlintRuleset(libs.ktlintCompose)

    lintChecks(libs.androidx.lint.gradle)
    lintChecks(libs.slack.lint.checks)
}

// firebase is just broken https://github.com/firebase/firebase-android-sdk/issues/6359
configurations {
    all {
        resolutionStrategy {
            // TODO
            activateDependencyLocking()
//            failOnVersionConflict() // TODO
        }
    }
    implementation {
        exclude(module = "protolite-well-known-types")
    }
}

kotlin {
    version = "2.1.20"
    sourceSets {
        all {
            languageSettings.progressiveMode = true
        }
    }
    compilerOptions {
        allWarningsAsErrors = true
        extraWarnings = true
        progressiveMode = true
//        https://kotlinlang.org/docs/whatsnew-eap.html#gradle
//        jvmDefault = JvmDefaultMode.NO_COMPATIBILITY
    }
}

hilt {
    enableAggregatingTask = true
}

protobuf {
    protoc {
        artifact = libs.com.google.protobuf.protoc.get().toString()
    }
    generateProtoTasks {
        all().forEach {
            it.builtins {
                create("java") {
                    option("lite")
                }
            }
        }
    }
}

aboutLibraries {
    license {
        strictMode = StrictMode.FAIL
        allowedLicenses.addAll("ASDKL") // Android SDK
        allowedLicenses.addAll("Apache-2.0")
        allowedLicenses.addAll("BSD License")
        allowedLicenses.addAll("BSD-3-Clause")
        allowedLicenses.addAll("CC0-1.0")
        allowedLicenses.addAll("EPL-1.0")
        allowedLicenses.addAll("Eclipse Public License v. 2.0")
        allowedLicenses.addAll("GNU General Public License, version 2 with the GNU Classpath Exception")
        allowedLicenses.addAll("MIT")
    }
    library {
        duplicationMode = DuplicateMode.LINK
        duplicationRule = DuplicateRule.EXACT
    }
}

ktlint {
    android = true
    coloredOutput = true
    version = "1.5.0"
}

dokka {
    moduleName = "app"
    dokkaSourceSets {
        main {
            suppressGeneratedFiles = true
            enableAndroidDocumentationLink = true
            enableJdkDocumentationLink = true
            enableKotlinStdLibDocumentationLink = true
            reportUndocumented = true
            documentedVisibilities =
                setOf(
                    VisibilityModifier.Public,
                    VisibilityModifier.Internal,
                    VisibilityModifier.Package,
                    VisibilityModifier.Protected,
                )
            sourceLink {
                localDirectory = (file("src/main/java"))
                remoteUrl("https://github.com/brokenkernel/improvtools")
                remoteLineSuffix = ("#L")
            }
        }
    }
    dokkaPublications {
        html {
            enabled = true
//            failOnWarning = true
        }
    }
    pluginsConfiguration {
        html {
            homepageLink = "https://improvtools.brokenkernel.com"
        }
        versioning {
        }
    }
}

@OptIn(ExperimentalKotlinGradlePluginApi::class)
powerAssert {
    functions =
        listOf(
            "kotlin.assert",
            "kotlin.test.assertEquals",
            "kotlin.test.assertTrue",
            "kotlin.test.assertNull",
            "kotlin.require",
            "kotlin.util.assert",
        )
    // have to list them all, since "detect all" doesn't work with android
    includedSourceSets = listOf(
        "debug",
        "debugAndroidTest",
        "debugUnitTest",
        "release",
        "releaseUnitTest",
    )
}

ksp {
    allWarningsAsErrors = true
    useKsp2 = true
    arg("dagger.useBindingGraphFix", "enabled")
    arg("dagger.ignoreProvisionKeyWildcards", "enabled")
    arg("dagger.experimentalDaggerErrorMessages", "enabled")
    arg("dagger.warnIfInjectionFactoryNotGeneratedUpstream", "enabled")
    arg("dagger.fullBindingGraphValidation", "error")
}

fun isStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.uppercase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable
}

tasks.withType<DependencyUpdatesTask> {
    checkConstraints = true
    checkBuildEnvironmentConstraints = true
    checkForGradleUpdate = true
    rejectVersionIf {
        when {
            // ideally allow non-stable updates of currently non-stable versions, but only if they match major versions
            !isStable(currentVersion) -> false
            !isStable(candidate.version) -> return@rejectVersionIf true
            (
                candidate.moduleIdentifier.toString() == "com.google.guava:guava" &&
                    !candidate.version.endsWith(
                        "android",
                    )
                ) -> return@rejectVersionIf true

            else -> return@rejectVersionIf false
        }
    }
}
