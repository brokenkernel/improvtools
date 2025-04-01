package com.brokenkernel.improvtools

import android.app.Application
import android.content.Context
import android.os.StrictMode
//import androidx.datastore.core.DataStoreFactory
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.HiltAndroidApp

private const val USER_SETTINGS_NAME = "user_settings"

@HiltAndroidApp
class ImprovToolsApplication() : Application() {

    private val Context.userPreferenceDataStore by preferencesDataStore(
        name = USER_SETTINGS_NAME
    )

    internal lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()

        val strictModeVMPolicy = StrictMode.VmPolicy.Builder()
            .detectActivityLeaks()
//            .detectBlockedBackgroundActivityLaunch()
            .detectCleartextNetwork()
            .detectContentUriWithoutPermission()
            .detectCredentialProtectedWhileLocked()
            .detectFileUriExposure()
            .detectImplicitDirectBoot()
            .detectIncorrectContextUse()
            .detectLeakedClosableObjects()
            .detectLeakedRegistrationObjects()
            .detectLeakedSqlLiteObjects()
            .detectNonSdkApiUsage()
            .detectUnsafeIntentLaunch()
            .detectUntaggedSockets()
            .penaltyDeath()
            .build()
        StrictMode.setVmPolicy(strictModeVMPolicy)
        val strictModeThreadPolicy = StrictMode.ThreadPolicy.Builder()
            .detectNetwork()
//            .detectDiskReads()
//            .detectDiskWrites()
            .detectExplicitGc()
            .detectCustomSlowCalls()
            .detectResourceMismatches()
            .detectUnbufferedIo()
            .penaltyDialog()
            .build()
        StrictMode.setThreadPolicy(strictModeThreadPolicy)
//        DataStoreFactory.create(USER_PREFERENCES_NAME)
        container = DefaultAppContainer(this.applicationContext.resources, userPreferenceDataStore)
    }
}