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
    namespace = "com.brokenkernel.improvtools.tonguetwister"
    compileSdk = 36

    defaultConfig {
        minSdk = 26

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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

kotlin {
    compilerOptions {
        // compose destinations
//        allWarningsAsErrors = true
    }
    explicitApi()
}

dependencies {
    api(libs.androidx.compose.material.materialIconsExtended)
    api(libs.androidx.lifecycle.viewmodel.savedstate)
    api(libs.androidx.material.icons.core)
    api(libs.androidx.navigation.common)
    api(libs.io.github.raamcosta.composeDestinations.core)

    implementation(enforcedPlatform(libs.androidx.compose.bom))
    implementation(enforcedPlatform(libs.kotlin.bom))
    implementation(project(":components"))
    implementation(libs.androidx.foundation)
    implementation(libs.androidx.foundation.layout)
    implementation(libs.androidx.glance.appwidget)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.runtime)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.ui.unit)
    implementation(libs.kotlinx.collections.immutable.jvm)

    debugImplementation(libs.ui.tooling)

    debugRuntimeOnly(libs.androidx.ui.test.manifest)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.monitor)
    androidTestImplementation(libs.junit)

    ksp(libs.io.github.raamcosta.composeDestinations.ksp)

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
