plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    alias(libs.plugins.ksp)
    id("com.brokenkernel.improvtools.sharedbuildlogic.common-kotlin-plugin")
}

kotlin {
    compilerOptions {
        allWarningsAsErrors = true
    }
    sourceSets {
        all {
            languageSettings.progressiveMode = true
        }
    }
}
