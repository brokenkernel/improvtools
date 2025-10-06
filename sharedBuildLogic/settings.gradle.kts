rootProject.name = "sharedBuildLogic"

plugins {
    id("dev.panuszewski.typesafe-conventions") version "0.8.1"
}

dependencyResolutionManagement {

    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
