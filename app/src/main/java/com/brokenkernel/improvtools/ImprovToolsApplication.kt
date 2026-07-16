package com.brokenkernel.improvtools

import android.app.Application
import android.os.Build
import android.os.StrictMode
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.firebase.Firebase
import com.google.firebase.crashlytics.crashlytics
import com.google.firebase.perf.performance
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
public class ImprovToolsApplication : Application() {

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

        Firebase.crashlytics.setCustomKey("buildconfig_build_type", BuildConfig.BUILD_TYPE)

        Firebase.performance.isPerformanceCollectionEnabled = true
        if (BuildConfig.ENABLE_CRASHLYTICS && isGooglePlayServicesAvailable()) {
            Firebase.performance.isPerformanceCollectionEnabled = true
            Firebase.crashlytics.isCrashlyticsCollectionEnabled = true
        }
    }
}
