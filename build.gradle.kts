plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.dagger.hilt.android) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.protobuf) apply false
    alias(libs.plugins.firebaseCrashlyticsPlugin) apply false
    alias(libs.plugins.firebasePerfPlugin) apply false
    alias(libs.plugins.google.gms.google.services) apply false
    alias(libs.plugins.dependencyAnalysis)
    alias(libs.plugins.sortDependencies) apply false
}

dependencyAnalysis {
    structure {
        // This should be false, and we could be more specific about what to add, but meh
        ignoreKtx(true)
    }
}