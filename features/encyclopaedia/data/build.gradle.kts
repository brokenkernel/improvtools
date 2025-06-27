plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
    id("com.brokenkernel.improvtools.sharedbuildlogic.common-kotlin-plugin")
}

kotlin {
    compilerOptions {
        allWarningsAsErrors = true
    }
    explicitApi()
}

dependencies {
    api(libs.kotlinx.collections.immutable.jvm)

    implementation(libs.androidx.collection.jvm)
    implementation(libs.extjwnl)

    compileOnly(libs.androidx.compose.material.materialIconsExtended)

    runtimeOnly(libs.extjwnl.data.wn31)
}
