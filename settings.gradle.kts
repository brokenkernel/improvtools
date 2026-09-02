pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "com.google.android.gms.oss-licenses-plugin") {
                useModule("com.google.android.gms:oss-licenses-plugin:0.13.0")
            }
        }
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}


plugins {
//    id("org.jetbrains.kotlinx.kover.aggregation") version "0.9.7"
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
rootProject.name = "ImprovTools"

enableFeaturePreview("STABLE_CONFIGURATION_CACHE")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
enableFeaturePreview("NO_IMPLICIT_LOOKUP_IN_PARENT_PROJECTS")
enableFeaturePreview("ENHANCED_GRAPH_ORDERING")

//kover {
//    enableCoverage()
//}


include(":app")
include(":components")
include(":features:debug")
include(":features:encyclopaedia:data")
include(":features:encyclopaedia:android")
include(":features:timer")
include(":features:suggestions")
include(":features:suggestions:data")
include(":features:suggestions:android")
include(":features:tonguetwister")
include(":coreinfra")
include(":features:tonguetwister:widget")
include(":features:buzzer")
