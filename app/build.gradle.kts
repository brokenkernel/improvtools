@file:OptIn(KspExperimental::class)

import com.android.build.api.dsl.VariantDimension
import com.google.devtools.ksp.KspExperimental
import com.google.firebase.crashlytics.buildtools.gradle.CrashlyticsExtension
import com.mikepenz.aboutlibraries.plugin.DuplicateMode
import com.mikepenz.aboutlibraries.plugin.DuplicateRule
import com.mikepenz.aboutlibraries.plugin.StrictMode
import java.io.IOException
import java.util.Properties
import org.gradle.kotlin.dsl.implementation
import org.jetbrains.dokka.gradle.engine.parameters.VisibilityModifier
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    alias(libs.plugins.aboutLibraries)
    alias(libs.plugins.android.application)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.firebaseCrashlyticsPlugin)
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.protobuf)
    alias(libs.plugins.firebasePerfPlugin)
    id("com.brokenkernel.improvtools.sharedbuildlogic.common-kotlin-plugin")
    id("com.brokenkernel.improvtools.sharedbuildlogic.common-general-plugin")
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
        versionCode = 216
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
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeCompiler {
        includeSourceInformation = true
        includeTraceMarkers = true
        featureFlags = setOf()
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
}

dependencies {
    implementation(enforcedPlatform(libs.androidx.compose.bom))
    implementation(enforcedPlatform(libs.com.google.firebase.firebaseBom))
    implementation(enforcedPlatform(libs.kotlin.bom))
    implementation(project(":components"))
    implementation(project(":features:encyclopaedia:android"))
    implementation(project(":features:encyclopaedia:data"))
    // TODO: remove the `:data` entry of suggestions
    implementation(project(":features:suggestions:data"))
    implementation(project(":features:timer"))
    implementation(libs.aboutlibraries.compose.m3)
    implementation(libs.aboutlibraries.core)
    implementation(libs.accompanist.permissions)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.animation)
    implementation(libs.androidx.animation.core)
    implementation(libs.androidx.annotation)
    implementation(libs.androidx.browser)
    implementation(libs.androidx.compose.material.materialIconsExtended)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.datastore)
    implementation(libs.androidx.datastore.core)
    implementation(libs.androidx.foundation)
    implementation(libs.androidx.foundation.layout)
    //    implementation(libs.androidx.glance.appwidget)
    //    implementation(libs.androidx.glance.material3)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.lifecycle.common)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewModel.compose)
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.lifecycle.viewmodel.savedstate)
    implementation(libs.androidx.material.icons.core)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.common)
    implementation(libs.androidx.navigation.navigationCompose)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.runtime)
    implementation(libs.androidx.runtime.saveable)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.text)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.ui.unit)
    implementation(libs.androidx.ui.util)
    implementation(libs.bundles.firebase)
    implementation(libs.com.google.protobuf.protobufJavalite)
    implementation(libs.config)
    implementation(libs.dagger.hilt.core)
    implementation(libs.fragment)
    implementation(libs.google.dagger)
    implementation(libs.guava)
    implementation(libs.hilt.android)
    implementation(libs.io.github.raamcosta.composeDestinations.core)
    implementation(libs.jakarta.inject.api)
    implementation(libs.javax.inject)
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinx.collections.immutable.jvm)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.serialization.core)
    implementation(libs.kotlinx.serialization.hocon)
    implementation(libs.mikepenz.aboutlibraries.compose.core)
    implementation(libs.navigation.runtime)
    implementation(libs.play.services.instantapps)
    implementation(libs.play.services.oss.licenses)
    implementation(libs.reorderable)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.reorderable.android.debug)

    runtimeOnly(libs.androidx.appsearch)
    runtimeOnly(libs.androidx.lifecycle.process)
    runtimeOnly(libs.androidx.profileinstaller)
    runtimeOnly(libs.androidx.runtime.tracing)
    runtimeOnly(libs.androidx.startup.runtime)
    runtimeOnly(libs.androidx.work.multiprocess)
    runtimeOnly(libs.slf4j.android)

    releaseRuntimeOnly(libs.leakcanary.object1.watcher.android)

    debugRuntimeOnly(libs.androidx.ui.test.manifest)
    // TODO: add profilable build
    // TODO: https://square.github.io/leakcanary/ui-tests/
    debugRuntimeOnly(libs.leakcanary.android)

    androidTestRuntimeOnly(libs.leakcanary.android.instrumentation)

    testImplementation(kotlin("test"))

    androidTestImplementation(libs.androidx.core)
    androidTestImplementation(libs.androidx.datastore)
    androidTestImplementation(libs.androidx.espresso.device)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.monitor)
    androidTestImplementation(libs.androidx.navigation.common)
    androidTestImplementation(libs.androidx.runner)
    androidTestImplementation(libs.androidx.runtime)
    androidTestImplementation(libs.androidx.ui.test)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation(libs.hamcrest)
    androidTestImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.kotlinx.coroutines.core)
    androidTestImplementation(libs.navigation.runtime)
    androidTestImplementation(libs.tools.fastlane.screengrab)

    dokkaPlugin(libs.android.documentation.plugin)
    dokkaPlugin(libs.mathjax.plugin)

    ksp(libs.androidx.appsearch.compiler)
    ksp(libs.androidx.lifecycle.compiler)
    // If this project uses any Kotlin source, use Kotlin Symbol Processing (KSP)
    // See Add the KSP plugin to your project
    ksp(libs.androidx.room.compiler)
    ksp(libs.hilt.compiler)
    ksp(libs.io.github.raamcosta.composeDestinations.ksp)

    ktlintRuleset(libs.ktlintCompose)

    lintChecks(libs.androidx.lint.gradle)
    lintChecks(libs.slack.lint.checks)
}

configurations {
    all {
        resolutionStrategy {
            failOnNonReproducibleResolution()
        }
    }
    // firebase is just broken https://github.com/firebase/firebase-android-sdk/issues/6359
    implementation {
        exclude(module = "protolite-well-known-types")
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

dokka {
    moduleName = "app"
    dokkaSourceSets {
        main {
            enableAndroidDocumentationLink = true
            enableJdkDocumentationLink = true
            enableKotlinStdLibDocumentationLink = true
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
    // have to list them all, since "detect all" doesn't work with android
    includedSourceSets = listOf(
        "debug",
        "debugAndroidTest",
        "debugUnitTest",
        "release",
        "releaseUnitTest",
    )
}
