package com.brokenkernel.improvtools.timer.sidecar.notifications

import android.app.NotificationManager
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object StopWatchNotificationModule {
    @Provides
    fun providesStopWatchNotificationManager(
        notificationManager: NotificationManager,
        @ApplicationContext appContext: Context,
    ): StopWatchNotificationManager {
        return StopWatchNotificationManager(notificationManager, appContext.resources)
    }

    @Provides
    fun providesCountDownNotificationManager(
        notificationManager: NotificationManager,
        @ApplicationContext appContext: Context,
    ): CountDownNotificationManager {
        return CountDownNotificationManager(notificationManager, appContext.resources)
    }
}
