package com.brokenkernel.improvtools

import android.app.Application
import android.os.StrictMode
import dagger.hilt.android.HiltAndroidApp


//private const val USER_SETTINGS_NAME = "user_settings.pb"
//
//private val Context.userPreferenceDataStore: DataStore<UserSettings> by dataStore(
//    fileName = USER_SETTINGS_NAME,
//    serializer = UserSettingsSerializer
//)

@HiltAndroidApp
class ImprovToolsApplication : Application() {

    // TODO: migrate to hilt instead of AppContainer
//    internal lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()


        val strictModeVMPolicy = StrictMode.VmPolicy.Builder()
            .detectActivityLeaks()
//            .detectBlockedBackgroundActivityLaunch() // requires sdk 36
            .detectCleartextNetwork()
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
//            .penaltyLog()
            .penaltyDeath()
            .build()
        StrictMode.setVmPolicy(strictModeVMPolicy)
        val strictModeThreadPolicy = StrictMode.ThreadPolicy.Builder()
            .detectNetwork()
//            .detectDiskReads() // TODO: move off to its own thread, used by settings
//            .detectDiskWrites() // TODO: move off to its own thread, used by settings
//            .detectExplicitGc() // TODO: used by tests(?)
            .detectCustomSlowCalls()
            .detectResourceMismatches()
            .detectUnbufferedIo()
//            .penaltyLog()
//            .penaltyDialog()
            .penaltyDeath()
            .build()
        StrictMode.setThreadPolicy(strictModeThreadPolicy)

//        DataStoreFactory.create(USER_PREFERENCES_NAME)
//        container = DefaultAppContainer(this.applicationContext.resources, userPreferenceDataStore)
    }
}
