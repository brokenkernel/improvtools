import com.android.build.api.dsl.VariantDimension
import java.io.IOException
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.protobuf)
    alias(libs.plugins.firebaseCrashlyticsPlugin)
//    alias(libs.plugins.firebasePerfPlugin)
    alias(libs.plugins.google.gms.google.services)
}

val DIMENSION_APP_STORE: String = "appstore_dimension"

val keystoreProperties: Properties = Properties()
var successfulLoadProperties: Boolean = false
try {
    rootProject.file("keystore.properties").inputStream().use { it ->
        keystoreProperties.load(it)
    }
    successfulLoadProperties = true
} catch (_: IOException) {
}

/**
 * Type safety for buildConfigField
 */
private inline fun <reified ValueT> VariantDimension.buildConfigField(name: String, value: ValueT): Unit {
    val resolvedValue: String = when (value) {
        is String -> "\"$value\""
        is Boolean -> "Boolean.parseBoolean(\"$value\")"
        else -> value.toString()
    }

    buildConfigField(ValueT::class.java.simpleName, name, resolvedValue)
}

android {
    namespace = "com.brokenkernel.improvtools"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.brokenkernel.improvtools"
        minSdk = 34
        targetSdk = 36
        versionCode = 50
        versionName = "0.0.$versionCode"

        testInstrumentationRunner = "com.brokenkernel.improvtools.infrastructure.ImprovToolsTestRunner"
    }

    signingConfigs {
        if (successfulLoadProperties) {
            create("config") {
                keyAlias = keystoreProperties["keyAlias"] as String
                keyPassword = keystoreProperties["keyPassword"] as String
                storeFile = file(keystoreProperties["storeFile"] as String)
                storePassword = keystoreProperties["storePassword"] as String
            }
        }
    }

    // https://developer.android.com/build/build-variants#filter-variants
    flavorDimensions += mutableListOf(DIMENSION_APP_STORE)
    productFlavors {
        create("playstore") {
            dimension = DIMENSION_APP_STORE
            versionNameSuffix = ".playstore"
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            if (successfulLoadProperties) {
                signingConfig = signingConfigs.getByName("config")
            }
            ndk {
                debugSymbolLevel = "FULL" // SYMBOL_TABLE - if it gets too big
            }
            buildConfigField("ENABLE_STRICT_MODE_DEATH", false)
            buildConfigField("ENABLE_CRASHLYTICS", true)
        }

        debug {
            buildConfigField("ENABLE_STRICT_MODE_DEATH", true)
            buildConfigField("ENABLE_CRASHLYTICS", false)
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    testOptions {
        managedDevices {
            localDevices {
                create("Pixel4Managed") {
                    device = "Pixel 4"
                    apiLevel = 35
                    systemImageSource = "google-atd"
                }
            }
        }
        unitTests {
            isIncludeAndroidResources = true
        }
    }
    buildToolsVersion = "35.0.0"
    dependenciesInfo {
        includeInApk = true
        includeInBundle = true
    }
}

dependencies {


    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.navigation.navigationTesting)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.tools.fastlane.screengrab)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    debugImplementation(libs.androidx.ui.test.manifest)
    debugImplementation(libs.androidx.ui.tooling)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material.extended)
    implementation(libs.androidx.compose.material3.adaptive.navigation.suite)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.datastore.datastorePreferences)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewModel.compose)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.navigationCompose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.com.fasterxml.jackson.module.jacksonModuleKotlin)
    implementation(libs.com.google.protobuf.protobufJavalite)
    implementation(libs.hilt.android)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.serialization.protobuf)
    implementation(platform(libs.androidx.compose.bom))
    testImplementation(libs.junit)
    implementation(platform(libs.com.google.firebase.firebaseBom))
    implementation(libs.bundles.firebase)

    ksp(libs.hilt.compiler)
    testImplementation(kotlin("test"))
}

hilt {
    enableAggregatingTask = true
}

protobuf {
    protoc {
        artifact = libs.com.google.protobuf.protoc.get().toString()
    }
    generateProtoTasks {
        all().forEach {
            it.builtins {
                create("java") {
                    option("lite")
                }
            }
        }
    }
}
