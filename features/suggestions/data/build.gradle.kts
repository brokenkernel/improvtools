plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.versions)
    alias(libs.plugins.sortDependencies)
    alias(libs.plugins.dependencyAnalysis)
    id("com.brokenkernel.improvtools.sharedbuildlogic.common-kotlin-plugin")
}

kotlin {
    compilerOptions {
        allWarningsAsErrors = true
    }
    explicitApi()
}

dependencies {
    implementation(libs.kotlinx.serialization.core)
}
