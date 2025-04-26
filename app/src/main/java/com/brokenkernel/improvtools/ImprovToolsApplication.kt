package com.brokenkernel.improvtools

import android.app.Application
import android.os.Build
import android.os.StrictMode
import android.util.Log
import android.util.Log.DEBUG
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.firebase.Firebase
import com.google.firebase.crashlytics.crashlytics
import com.google.firebase.remoteconfig.ConfigUpdate
import com.google.firebase.remoteconfig.ConfigUpdateListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import dagger.hilt.android.HiltAndroidApp
import kotlin.time.Duration.Companion.hours

fun configureRemoteConfig() {
    val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
    val configSettings = remoteConfigSettings {
        minimumFetchIntervalInSeconds = 1.hours.inWholeSeconds
    }
    remoteConfig.setConfigSettingsAsync(configSettings)

    remoteConfig.addOnConfigUpdateListener(
        object : ConfigUpdateListener {
            override fun onUpdate(configUpdate: ConfigUpdate) {
                if (Log.isLoggable(TAG, DEBUG)) {
                    Log.d(TAG, "Updated keys: " + configUpdate.updatedKeys)
                }
            }

            override fun onError(error: FirebaseRemoteConfigException) {
                Log.w(TAG, "Config update error with code: " + error.code, error)
            }
        },
    )
}

@HiltAndroidApp
class ImprovToolsApplication : Application() {

    private fun isGooglePlayServicesAvailable(): Boolean {
        val googleApiAvailability = GoogleApiAvailability.getInstance()
        val status = googleApiAvailability.isGooglePlayServicesAvailable(this)
        return status == ConnectionResult.SUCCESS
    }

    override fun onCreate() {
        super.onCreate()

        val strictModeVMPolicy = StrictMode.VmPolicy.Builder()
            .detectActivityLeaks()
//            .detectBlockedBackgroundActivityLaunch() // requires sdk 36
            .detectCleartextNetwork() // TODO: Firebase causes this?
            .detectContentUriWithoutPermission()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            strictModeVMPolicy.detectCredentialProtectedWhileLocked()
                .detectImplicitDirectBoot()
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            strictModeVMPolicy
                .detectIncorrectContextUse()
                .detectUnsafeIntentLaunch()
        }
        strictModeVMPolicy.detectFileUriExposure()
            .detectLeakedClosableObjects()
            .detectLeakedRegistrationObjects()
            .detectLeakedSqlLiteObjects()
//            .detectNonSdkApiUsage() // TODO: used by ScreenshotGenerationTest WRONG API
            .detectUntaggedSockets()
            .penaltyLog()

        val strictModeThreadPolicy = StrictMode.ThreadPolicy.Builder()
            .detectNetwork()
            .detectDiskReads() // TODO: move off to its own thread, used by settings
            .detectDiskWrites() // TODO: move off to its own thread, used by settings
//            .detectExplicitGc() // TODO: used by tests(?). Wrong API
            .detectCustomSlowCalls()
            .detectResourceMismatches()
            .detectUnbufferedIo()
            .penaltyLog()

        if (BuildConfig.ENABLE_STRICT_MODE_DEATH) {
            // TODO: firebase now causes this
//            strictModeVMPolicy.penaltyDeath()
//            strictModeThreadPolicy.penaltyDeath()
        } else {
            strictModeVMPolicy.penaltyDeathOnCleartextNetwork()
            strictModeThreadPolicy.penaltyLog()
        }
        StrictMode.setVmPolicy(strictModeVMPolicy.build())
        StrictMode.setThreadPolicy(strictModeThreadPolicy.build())

        configureRemoteConfig()

        if (BuildConfig.ENABLE_CRASHLYTICS && isGooglePlayServicesAvailable()) {
//            Firebase.performance.isPerformanceCollectionEnabled = true
            Firebase.crashlytics.isCrashlyticsCollectionEnabled = true
        }
    }
}
