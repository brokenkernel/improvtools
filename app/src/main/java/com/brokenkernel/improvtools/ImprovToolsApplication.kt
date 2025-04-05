package com.brokenkernel.improvtools

import android.app.Application
import android.os.StrictMode
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.hilt.android.HiltAndroidApp

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
            .detectCredentialProtectedWhileLocked()
            .detectFileUriExposure()
            .detectImplicitDirectBoot()
            .detectIncorrectContextUse()
            .detectLeakedClosableObjects()
            .detectLeakedRegistrationObjects()
            .detectLeakedSqlLiteObjects()
//            .detectNonSdkApiUsage() // TODO: used by ScreenshotGenerationTest
            .detectUnsafeIntentLaunch()
            .detectUntaggedSockets()
            .penaltyLog()


        val strictModeThreadPolicy = StrictMode.ThreadPolicy.Builder()
            .detectNetwork()
//            .detectDiskReads() // TODO: move off to its own thread, used by settings
//            .detectDiskWrites() // TODO: move off to its own thread, used by settings
//            .detectExplicitGc() // TODO: used by tests(?)
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

        if (BuildConfig.ENABLE_CRASHLYTICS && isGooglePlayServicesAvailable()) {
            FirebaseCrashlytics.getInstance()
                .isCrashlyticsCollectionEnabled = true
        }
    }
}