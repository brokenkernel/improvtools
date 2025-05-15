plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
    alias(libs.plugins.sortDependencies)
    alias(libs.plugins.ktlint)
    kotlin("plugin.power-assert") version libs.versions.kotlin.get()
}

group = "com.brokenkernel.improvtools.commonBuildLogic"

kotlin {
    // TODO: explicitAPI for _all_ builds (convention!)
    explicitApi()
}

gradlePlugin {
    plugins {
        register("commonLibraryPlugin") {
            id = "com.brokenkernel.improvtools.sharedbuildlogic.common-library-plugin"
            implementationClass = "com.brokenkernel.improvtools.sharedbuildlogic.CommonLibraryPlugin"
        }
        register("commonGeneralPlugin") {
            id = "com.brokenkernel.improvtools.sharedbuildlogic.common-general-plugin"
            implementationClass = "com.brokenkernel.improvtools.sharedbuildlogic.CommonGeneralPlugin"
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
