plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.sortDependencies)
    alias(libs.plugins.dependencyAnalysis)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.detekt)
    kotlin("plugin.power-assert") version libs.versions.kotlin.get()
}

kotlin {
    compilerOptions {
        allWarningsAsErrors = true
        extraWarnings = true
    }
    explicitApi()
}

dependencies {
    api(libs.kotlinx.collections.immutable.jvm)

    implementation(libs.androidx.collection.jvm)
    implementation(libs.extjwnl)
    implementation(libs.kotlinx.serialization.core)
    implementation(libs.kotlinx.serialization.hocon)

    compileOnly(libs.androidx.compose.material.materialIconsExtended)

    runtimeOnly(libs.extjwnl.data.wn31)

    detektPlugins(libs.composeDetektRules)
    detektPlugins(libs.detektRulesLibraries)
}

ktlint {
    android.set(true)
    coloredOutput.set(true)
    version.set("1.8.0")
}
