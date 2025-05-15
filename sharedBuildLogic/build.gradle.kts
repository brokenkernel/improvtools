plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
    alias(libs.plugins.sortDependencies)
    alias(libs.plugins.ktlint)
}

group = "com.brokenkernel.improvtools.commonBuildLogic"

kotlin {
    // TODO: explicitAPI for _all_ builds (convention!)
    explicitApi()
}

gradlePlugin {
    plugins {
        register("androidLibrary") {
            id = "com.brokenkernel.improvtools.sharedbuildlogic.common-library-plugin"
            implementationClass = "com.brokenkernel.improvtools.sharedbuildlogic.CommonLibraryPlugin"
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
}
