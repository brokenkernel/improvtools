import org.gradle.kotlin.dsl.dokkaPublications

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
    alias(libs.plugins.ktlint) apply false
    alias(libs.plugins.spotless) apply false
    alias(libs.plugins.dokka)

    kotlin("plugin.power-assert") version "2.1.20" apply false
}

dependencies {
    dokka(project(":app:"))
    dokkaPlugin(libs.android.documentation.plugin)
    dokkaHtmlPlugin(libs.kotlin.as1.java.plugin)
    dokkaPlugin(libs.mathjax.plugin)
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

dokka {
    dokkaPublications {
        html {
            enabled = true
            // outputDirectory = rootDir.resolve("docs/static/api")
            includes.from(project.layout.projectDirectory.file("README.md"))
        }
    }
}
