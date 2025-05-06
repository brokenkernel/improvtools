@file:OptIn(KspExperimental::class)

import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import com.google.devtools.ksp.KspExperimental
import org.gradle.kotlin.dsl.withType
import org.jetbrains.dokka.gradle.engine.parameters.VisibilityModifier
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeFeatureFlag
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

// TODO: move of this out to shared-build-conventions

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.dependencyAnalysis)
    alias(libs.plugins.dokka)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.sortDependencies)
    id("shared-build-conventions")

    kotlin("plugin.power-assert") version "2.1.20"
}

android {
    namespace = "com.brokenkernel.components"
    compileSdk = 36

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
    buildToolsVersion = "35.0.0"
    lint {
        lintConfig = file("lint.xml")
        baseline = file("lint-baseline.xml")
        checkDependencies = true
        warningsAsErrors = true
    }
    composeCompiler {
        includeSourceInformation = true
        includeTraceMarkers = true
        featureFlags = setOf(
            ComposeFeatureFlag.OptimizeNonSkippingGroups,
            ComposeFeatureFlag.PausableComposition,
        )
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

dependencies {
    implementation(platform(libs.androidx.compose.bom)) {
        because("we are an android compose application")
    }
    implementation(platform(libs.kotlin.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.annotation)
    implementation(libs.androidx.compose.material.extended)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.loader)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material3.adaptive)
    implementation(libs.androidx.material3.adaptive.layout)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.hilt.android)
    implementation(libs.kotlinx.collections.immutable.jvm)
    implementation(libs.material)

    debugRuntimeOnly(libs.androidx.ui.test.manifest)

    testImplementation(libs.junit)

    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.ui.test.junit4)

    ksp(libs.hilt.compiler)

    ktlintRuleset(libs.ktlintCompose)

    lintChecks(libs.androidx.lint.gradle)
}

ktlint {
    android = true
    coloredOutput = true
    version = "1.5.0"
}

dokka {
    moduleName = "components"
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
