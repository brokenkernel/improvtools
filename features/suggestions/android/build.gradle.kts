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
    namespace = "com.brokenkernel.improvtools.android"

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
    implementation(enforcedPlatform(libs.androidx.compose.bom))
    implementation(enforcedPlatform(libs.kotlin.bom))

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
