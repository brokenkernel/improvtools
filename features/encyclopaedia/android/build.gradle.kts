import com.android.build.api.dsl.LibraryExtension
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.sortDependencies)
    alias(libs.plugins.dependencyAnalysis)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.detekt)
    alias(libs.plugins.dokka)
    kotlin("plugin.power-assert") version libs.versions.kotlin.get()
}

configure<LibraryExtension> {
    namespace = "com.brokenkernel.improvtools.encyclopaedia.android"

    compileSdk = 37

    defaultConfig {
        testInstrumentationRunner =
            "com.brokenkernel.improvtools.coreinfra.ImprovToolsTestRunner"
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
//        false warnings  Assigned value is never read.
//        allWarningsAsErrors = true
    }
}

dependencies {
    api(libs.androidx.foundation.layout)
    api(libs.androidx.lifecycle.viewmodel.savedstate)
    api(libs.androidx.navigation.common)
    api(libs.androidx.runtime)
    api(libs.androidx.ui)
    api(libs.io.github.raamcosta.composeDestinations.core)
    api(projects.features.encyclopaedia.data)

    implementation(enforcedPlatform(libs.androidx.compose.bom))
    implementation(enforcedPlatform(libs.kotlin.bom))
    implementation(libs.androidx.animation)
    implementation(libs.androidx.compose.material.materialIconsExtended)
    implementation(libs.androidx.foundation)
    implementation(libs.androidx.material.icons.core)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.runtime.saveable)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.text)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.ui.unit)
    implementation(libs.kotlinx.collections.immutable.jvm)
    implementation(projects.components)
    implementation(projects.coreinfra)

    debugRuntimeOnly(libs.androidx.ui.test.manifest)

    androidTestRuntimeOnly(testFixtures(projects.coreinfra))
    androidTestRuntimeOnly(libs.androidx.core)
    androidTestRuntimeOnly(libs.androidx.runner)
    androidTestRuntimeOnly(libs.kotlinx.coroutines.test)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.activity)
    androidTestImplementation(libs.androidx.annotation)
    androidTestImplementation(libs.androidx.core)
    androidTestImplementation(libs.androidx.espresso.device)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.lifecycle.viewmodel)
    androidTestImplementation(libs.androidx.navigation.common)
    androidTestImplementation(libs.androidx.runtime)
    androidTestImplementation(libs.androidx.ui.test)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation(libs.dagger.hilt.core)
    androidTestImplementation(libs.fragment)
    androidTestImplementation(libs.google.dagger)
    androidTestImplementation(libs.guava)
    androidTestImplementation(libs.hilt.android)
    //    androidTestImplementation(libs.hamcrest)
    androidTestImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.navigation.runtime)
    androidTestImplementation(projects.coreinfra)

    detektPlugins(libs.composeDetektRules)
    detektPlugins(libs.detektRulesLibraries)

    // kspTest(?)
    ksp(libs.hilt.compiler)
    ksp(libs.io.github.raamcosta.composeDestinations.ksp)
    ksp(libs.kotlin.metadata.jvm)

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
    arg("compose-destinations.moduleName", "encyclopaedia")
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

hilt {
    enableAggregatingTask = true
}
