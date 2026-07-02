import com.android.build.api.dsl.LibraryExtension
import org.jetbrains.dokka.gradle.engine.parameters.VisibilityModifier
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
    namespace = "com.brokenkernel.improvtools.features.debug"

    compileSdk = 37

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
    explicitApi()
    compilerOptions {
        allWarningsAsErrors = true
    }
}

// dokka {
//    moduleName = "debug"
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

dependencies {
    api(libs.androidx.runtime)

    implementation(enforcedPlatform(libs.androidx.compose.bom))
    implementation(enforcedPlatform(libs.kotlin.bom))

    debugRuntimeOnly(libs.androidx.ui.test.manifest)

    testImplementation(libs.junit)

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

dokka {
    moduleName = project.name
    dokkaSourceSets {
        configureEach {
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
            failOnWarning = true
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