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
    explicitApi()
}

dependencies {
    implementation(libs.androidx.collection.jvm)
    implementation(libs.androidx.compose.material.materialIconsExtended)
    implementation(libs.extjwnl)
    implementation(libs.kotlinx.collections.immutable.jvm)

    runtimeOnly(libs.extjwnl.data.wn31)
}
