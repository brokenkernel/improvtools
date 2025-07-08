rootProject.name = "sharedBuildLogic"

plugins {
    id("dev.panuszewski.typesafe-conventions") version "0.7.4"
}

dependencyResolutionManagement {

    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
