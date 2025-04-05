package com.brokenkernel.improvtools

import android.app.Application
import android.os.StrictMode
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ImprovToolsApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val strictModeVMPolicy = StrictMode.VmPolicy.Builder()
            .detectActivityLeaks()
//            .detectBlockedBackgroundActivityLaunch() // requires sdk 36
//            .detectCleartextNetwork()
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
//            strictModeVMPolicy.penaltyDeath()
//            strictModeThreadPolicy.penaltyDeath()
        } else {
            strictModeVMPolicy.penaltyDeathOnCleartextNetwork()
            strictModeThreadPolicy.penaltyLog()
        }
        StrictMode.setVmPolicy(strictModeVMPolicy.build())
        StrictMode.setThreadPolicy(strictModeThreadPolicy.build())
    }
}