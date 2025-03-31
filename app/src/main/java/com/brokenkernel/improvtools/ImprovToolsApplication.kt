package com.brokenkernel.improvtools

import android.app.Application

class ImprovToolsApplication(): Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this.applicationContext.resources)
    }
}