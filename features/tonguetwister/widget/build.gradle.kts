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
    kotlin("plugin.power-assert") version libs.versions.kotlin.get()
}

configure<LibraryExtension> {
    namespace = "com.brokenkernel.improvtools.tonguetwister.widget"
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
    implementation(libs.androidx.glance)
    implementation(libs.androidx.glance.material3)
    implementation(libs.androidx.glance.preview)
    implementation(libs.androidx.ui.text)

    debugImplementation(libs.ui.tooling)

    runtimeOnly(libs.androidx.glance.appwidget)

    debugRuntimeOnly(libs.androidx.ui.test.manifest)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.monitor)
    androidTestImplementation(libs.junit)

    detektPlugins(libs.composeDetektRules)

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

ksp {
    arg("compose-destinations.mermaidGraph", "$rootDir/docs/static/")
    arg("compose-destinations.htmlMermaidGraph", "$rootDir/docs/static/")
    allWarningsAsErrors = true
    arg("dagger.useBindingGraphFix", "enabled")
    arg("dagger.ignoreProvisionKeyWildcards", "enabled")
    arg("dagger.experimentalDaggerErrorMessages", "enabled")
    arg("dagger.warnIfInjectionFactoryNotGeneratedUpstream", "enabled")
    arg("dagger.fullBindingGraphValidation", "error")
}

ktlint {
    android.set(true)
    coloredOutput.set(true)
    version.set("1.8.0")
}
