rootProject.name = "sharedBuildLogic"

plugins {
    id("dev.panuszewski.typesafe-conventions") version "0.8.0"
}

dependencyResolutionManagement {

    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
