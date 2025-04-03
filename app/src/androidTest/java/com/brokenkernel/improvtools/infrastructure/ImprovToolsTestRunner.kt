package com.brokenkernel.improvtools.infrastructure

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.testing.HiltTestApplication

@Suppress("unused") // used by gradle
class ImprovToolsTestRunner : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader?, name: String?, context: Context?): Application {
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }
}