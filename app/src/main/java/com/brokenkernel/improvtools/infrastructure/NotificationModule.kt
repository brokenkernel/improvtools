package com.brokenkernel.improvtools.infrastructure

import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NotificationModule {

    @Provides
    fun providesNotificationManager(@ApplicationContext appContext: Context): NotificationManager {
        return appContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    }
}
