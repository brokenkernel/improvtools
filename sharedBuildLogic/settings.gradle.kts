rootProject.name = "sharedBuildLogic"

plugins {
    id("dev.panuszewski.typesafe-conventions") version "0.9.1"
}

dependencyResolutionManagement {

    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
