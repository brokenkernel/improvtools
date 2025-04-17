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
    alias(libs.plugins.aboutLibraries) apply false
}

dependencyAnalysis {
    useTypesafeProjectAccessors(true)
    usage {
        analysis {
            checkSuperClasses(true)
        }
    }
    structure {
        // This should be false, and we could be more specific about what to add, but meh
        ignoreKtx(true)
    }
    issues {
        all {
            // This really should only be _directly used_ transitive deps. I do care about those
            // but this test includes e.g., guava which I _don't_ directly use and thus should be excluded.
            onUsedTransitiveDependencies {
                severity("ignore")
            }
        }
    }
}