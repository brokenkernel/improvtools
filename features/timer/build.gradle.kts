import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    id("com.brokenkernel.improvtools.sharedbuildlogic.common-general-plugin")
    id("com.brokenkernel.improvtools.sharedbuildlogic.common-library-plugin")
}

android {
    namespace = "com.brokenkernel.improvtools.timer"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }

    composeCompiler {
        includeSourceInformation = true
        includeTraceMarkers = true
        featureFlags = setOf()
    }
}

kotlin {
    explicitApi()
    compilerOptions {
        allWarningsAsErrors = true
    }
}

dependencies {
    api(libs.androidx.runtime)
    api(libs.androidx.ui)

    debugApi(libs.reorderable.android.debug)

    implementation(project(":components"))
    implementation(libs.androidx.compose.material.materialIconsExtended)
    implementation(libs.androidx.foundation)
    implementation(libs.androidx.foundation.layout)
    implementation(libs.androidx.lifecycle.common)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.material.icons.core)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.text)
    implementation(libs.androidx.ui.unit)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.reorderable)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.monitor)
    androidTestImplementation(libs.junit)

    ktlintRuleset(libs.ktlintCompose)

    lintChecks(libs.androidx.lint.gradle)
    lintChecks(libs.slack.lint.checks)
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
