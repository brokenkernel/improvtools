import org.jetbrains.kotlin.gradle.dsl.JvmDefaultMode
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
    alias(libs.plugins.sortDependencies)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.versions)
    kotlin("plugin.power-assert") version libs.versions.kotlin.get()
}

group = "com.brokenkernel.improvtools.commonBuildLogic"

kotlin {
    version = "2.2.0-alpha2"
    compilerOptions {
        languageVersion.set(KotlinVersion.KOTLIN_2_3)
        apiVersion.set(KotlinVersion.KOTLIN_2_3)
        extraWarnings.set(true)
        progressiveMode.set(true)
        jvmDefault.set(JvmDefaultMode.NO_COMPATIBILITY)
    }
    jvmToolchain(21)
    // TODO: explicitAPI for _all_ builds (convention!)
    explicitApi()
}

gradlePlugin {
    plugins {
        register("commonLibraryPlugin") {
            id = "com.brokenkernel.improvtools.sharedbuildlogic.common-library-plugin"
            implementationClass = "com.brokenkernel.improvtools.sharedbuildlogic.CommonLibraryPlugin"
        }
        register("commonGeneralAndroidPlugin") {
            id = "com.brokenkernel.improvtools.sharedbuildlogic.common-general-plugin"
            implementationClass = "com.brokenkernel.improvtools.sharedbuildlogic.CommonGeneralAndroidPlugin"
        }
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
    compileOnly(libs.gradlePlugins.gradleVersions)
    compileOnly(libs.gradlePlugins.ksp)
    compileOnly(libs.gradlePlugins.kotlin)
    compileOnly(libs.gradlePlugins.sortDependencies)
    compileOnly(libs.gradlePlugins.ktlint)
    compileOnly(libs.gradlePlugins.powerAssert)
}
