import org.jetbrains.dokka.gradle.engine.parameters.VisibilityModifier
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
    namespace = "com.brokenkernel.improvtools.features.debug"
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    compilerOptions {
        allWarningsAsErrors = true
    }
}

dokka {
    moduleName = "debug"
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

dependencies {
    api(libs.androidx.runtime)

    implementation(enforcedPlatform(libs.androidx.compose.bom))
    implementation(enforcedPlatform(libs.kotlin.bom))

    debugRuntimeOnly(libs.androidx.ui.test.manifest)

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
