import com.android.build.api.dsl.VariantDimension
import com.google.firebase.crashlytics.buildtools.gradle.CrashlyticsExtension
import com.mikepenz.aboutlibraries.plugin.DuplicateMode
import com.mikepenz.aboutlibraries.plugin.DuplicateRule
import com.mikepenz.aboutlibraries.plugin.StrictMode
import org.gradle.kotlin.dsl.implementation
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
    alias(libs.plugins.dependencyAnalysis)
    alias(libs.plugins.sortDependencies)
    alias(libs.plugins.aboutLibraries)
}

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
        minSdk = 26
        targetSdk = 36
        versionCode = 131
        versionName = "0.0.$versionCode"

        testInstrumentationRunner = "com.brokenkernel.improvtools.infrastructure.ImprovToolsTestRunner"
        proguardFiles(
            getDefaultProguardFile("proguard-android-optimize.txt"),
            "proguard-rules.pro"
        )

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

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            if (successfulLoadProperties) {
                signingConfig = signingConfigs.getByName("config")
            }
            ndk {
                debugSymbolLevel = "FULL" // SYMBOL_TABLE - if it gets too big
            }
            buildConfigField("ENABLE_STRICT_MODE_DEATH", false)
            buildConfigField("ENABLE_CRASHLYTICS", true)
            configure<CrashlyticsExtension> {
                nativeSymbolUploadEnabled = true
                mappingFileUploadEnabled = true
            }
        }

        debug {
            buildConfigField("ENABLE_STRICT_MODE_DEATH", true)
            buildConfigField("ENABLE_CRASHLYTICS", false)
            isPseudoLocalesEnabled = true
            configure<CrashlyticsExtension> {
                nativeSymbolUploadEnabled = true
                mappingFileUploadEnabled = true
            }
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
                create("Nexus9Managed") {
                    device = "Nexus9"
                    apiLevel = 35
                    systemImageSource = "google-atd"
                }
            }
            groups {
                create("phoneAndTablet") {
                    targetDevices.add(allDevices["Pixel4Managed"])
                    targetDevices.add(allDevices["Nexus9Managed"])
                }
            }
        }
        unitTests {
            isIncludeAndroidResources = true
        }
        emulatorControl {
            enable = true
        }
    }
    buildToolsVersion = "35.0.0"
    dependenciesInfo {
        includeInApk = true
        includeInBundle = true
    }
    lint {
        lintConfig = file("lint.xml")
        baseline = file("lint-baseline.xml")
        checkDependencies = true
        warningsAsErrors = true
    }
    packaging {
        jniLibs {
            // unable to strip, so shut up warnings
            keepDebugSymbols.add("**/libandroidx.graphics.path.so")
            keepDebugSymbols.add("**/libdatastore_shared_counter.so")
        }
    }
    androidResources {
        generateLocaleConfig = true
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(platform(libs.com.google.firebase.firebaseBom))
    implementation(libs.aboutlibraries.compose.m3)
    implementation(libs.aboutlibraries.core)
    implementation(libs.androidbrowserhelper)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.annotation)
    implementation(libs.androidx.browser)
    implementation(libs.androidx.compose.material.extended)
    implementation(libs.androidx.compose.material3.adaptive.navigation.suite)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.datastore)
    implementation(libs.androidx.datastore.core)
    implementation(libs.androidx.glance.appwidget)
    implementation(libs.androidx.glance.material3)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.input.motionprediction)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewModel.compose)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.loader)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.navigationCompose)
    implementation(libs.androidx.profileinstaller)
    implementation(libs.androidx.startup.runtime)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.window)
    implementation(libs.androidx.work.multiprocess)
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.bundles.firebase)
    implementation(libs.com.google.protobuf.protobufJavalite)
    implementation(libs.firebase.config)
    implementation(libs.guava)
    implementation(libs.hilt.android)
    implementation(libs.javax.inject)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.serialization.protobuf)
    implementation(libs.net.engawapg.lib.zoomable)
    implementation(libs.play.services.instantapps)

    debugImplementation(libs.androidx.ui.test.manifest)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.leakcanary.android)

    testImplementation(kotlin("test"))
    testImplementation(libs.junit)

    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.activity)
    androidTestImplementation(libs.androidx.datastore)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.espresso.device)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.navigation.navigationTesting)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation(libs.androidx.work.testing)
    androidTestImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.tools.fastlane.screengrab)

    ksp(libs.hilt.compiler)
}

// firebase is just broken https://github.com/firebase/firebase-android-sdk/issues/6359
configurations {
    implementation {
        exclude(module = "protolite-well-known-types")
    }
}

kotlin {
    compilerOptions {
        allWarningsAsErrors = true
        extraWarnings = true
        progressiveMode = true
    }
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

aboutLibraries {
    strictMode = StrictMode.FAIL
    duplicationMode = DuplicateMode.LINK
    duplicationRule = DuplicateRule.EXACT
}
