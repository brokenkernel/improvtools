package com.brokenkernel.improvtools.timer.sidecar.timermanager

import com.brokenkernel.improvtools.timer.data.repository.DefaultTimerManager
import com.brokenkernel.improvtools.timer.data.repository.TimerManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TimerManagerModule {
    @Singleton
    @Provides
    fun providesTimerManager(): TimerManager {
        return DefaultTimerManager()
    }
}
