plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
}

kotlin {
    version = libs.versions.kotlin.get()
}
