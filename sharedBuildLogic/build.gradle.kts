import org.jetbrains.kotlin.gradle.dsl.JvmDefaultMode
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
    alias(libs.plugins.dependencyAnalysis)
    alias(libs.plugins.sortDependencies)
    alias(libs.plugins.ktlint)
    kotlin("plugin.power-assert") version libs.versions.kotlin.get()
}

group = "com.brokenkernel.improvtools.commonBuildLogic"

kotlin {
    compilerOptions {
        languageVersion = KotlinVersion.KOTLIN_2_3
        apiVersion = KotlinVersion.KOTLIN_2_3
        allWarningsAsErrors = true
        extraWarnings = true
        jvmDefault = JvmDefaultMode.NO_COMPATIBILITY
    }
    jvmToolchain(21)
    explicitApi()
}

gradlePlugin {
    plugins {
        register("commonKotlinPlugin") {
            id = "com.brokenkernel.improvtools.sharedbuildlogic.common-kotlin-plugin"
            implementationClass = "com.brokenkernel.improvtools.sharedbuildlogic.CommonKotlinPlugin"
        }

    }
}

dependencies {
    compileOnly(libs.gradlePlugins.android)
    compileOnly(libs.gradlePlugins.dependencyAnalysis)
    compileOnly(libs.gradlePlugins.dokka)
    compileOnly(libs.gradlePlugins.kotlin)
    compileOnly(libs.gradlePlugins.powerAssert)
}

ktlint {
    android.set(true)
    coloredOutput.set(true)
    version.set("1.8.0")
}
