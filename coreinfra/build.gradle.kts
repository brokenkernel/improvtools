import com.android.build.api.dsl.LibraryExtension
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.sortDependencies)
    alias(libs.plugins.dependencyAnalysis)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.detekt)
    alias(libs.plugins.dokka)
    kotlin("plugin.power-assert") version libs.versions.kotlin.get()
}

configure<LibraryExtension> {
    namespace = "com.brokenkernel.improvtools.coreinfra"
    compileSdk = 37

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

    lint {
        lintConfig = file("lint.xml")
        baseline = file("lint-baseline.xml")
        checkDependencies = true
        warningsAsErrors = true
    }

    defaultConfig {
        minSdk = 26
        consumerProguardFiles("consumer-rules.pro")
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

composeCompiler {
    includeSourceInformation = true
    includeTraceMarkers = true
    featureFlags = setOf()
}

kotlin {
    compilerOptions {
        allWarningsAsErrors = true
    }
    explicitApi()
}

dependencies {
    api(libs.androidx.runtime)

    implementation(enforcedPlatform(libs.androidx.compose.bom))
    implementation(enforcedPlatform(libs.kotlin.bom))
    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.kotlinx.coroutines.core)

    debugRuntimeOnly(libs.androidx.ui.test.manifest)

    androidTestRuntimeOnly(libs.androidx.runner)
    androidTestRuntimeOnly(libs.kotlinx.coroutines.test)

    testFixturesApi(libs.androidx.runner)
    testFixturesApi(libs.androidx.runtime)
    testFixturesApi(libs.kotlinx.coroutines.core)

    testFixturesImplementation(libs.hilt.android.testing)

    testImplementation(libs.junit)

    testRuntimeOnly(libs.androidx.runner)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.monitor)
    androidTestImplementation(libs.junit)

    detektPlugins(libs.composeDetektRules)
    detektPlugins(libs.detektRulesLibraries)

    ktlintRuleset(libs.ktlintCompose)

    lintChecks(libs.android.securityLint)
    lintChecks(libs.androidx.lint.gradle)
    lintChecks(libs.slack.lint.checks)
    lintChecks(libs.slack.lint.checks.compose)
}

// dokka {
//    moduleName = "coreinfra"
//    dokkaSourceSets {
//        main {
//            enableAndroidDocumentationLink = true
//            enableJdkDocumentationLink = true
//            enableKotlinStdLibDocumentationLink = true
//            documentedVisibilities =
//                setOf(
//                    VisibilityModifier.Public,
//                    VisibilityModifier.Internal,
//                    VisibilityModifier.Package,
//                    VisibilityModifier.Protected,
//                )
//            sourceLink {
//                localDirectory = (file("src/main/java"))
//                remoteUrl("https://github.com/brokenkernel/improvtools")
//                remoteLineSuffix = ("#L")
//            }
//        }
//    }
//    dokkaPublications {
//        html {
//            enabled = true
// //            failOnWarning = true
//        }
//    }
//    pluginsConfiguration {
//        html {
//            homepageLink = "https://improvtools.brokenkernel.com"
//        }
//        versioning {
//        }
//    }
// }

android {
    testFixtures {
        enable = true
        androidResources = true
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

ksp {
    arg("compose-destinations.moduleName", project.name)
    arg("compose-destinations.mermaidGraph", "$rootDir/docs/static/")
    arg("compose-destinations.htmlMermaidGraph", "$rootDir/docs/static/")
    allWarningsAsErrors = true
    arg("dagger.useBindingGraphFix", "enabled")
    arg("dagger.ignoreProvisionKeyWildcards", "enabled")
    arg("dagger.experimentalDaggerErrorMessages", "enabled")
    arg("dagger.warnIfInjectionFactoryNotGeneratedUpstream", "enabled")
    arg("dagger.fullBindingGraphValidation", "error")
    arg("dagger.mapMultibindingDuplicateDetectionFix", "enabled")
}

ktlint {
    android.set(true)
    coloredOutput.set(true)
    version.set("1.8.0")
}
