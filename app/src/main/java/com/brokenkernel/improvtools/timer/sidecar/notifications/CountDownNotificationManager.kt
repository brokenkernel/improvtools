package com.brokenkernel.improvtools.timer.sidecar.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.res.Resources
import android.os.Build
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.sidecar.notifications.ImprovToolsNotification
import com.brokenkernel.improvtools.sidecar.notifications.ImprovToolsNotificationManager
import javax.inject.Inject
import kotlin.time.Duration

internal class CountDownhNotification(id: Int, notification: Notification) :
    ImprovToolsNotification(id, notification)

internal class CountDownNotificationManager @Inject constructor(
    notificationManager: NotificationManager,
    resources: Resources,
) : ImprovToolsNotificationManager<StopWatchNotification>(notificationManager) {
    private val countDownNotificationChannel: NotificationChannel = NotificationChannel(
        this::class.toString(),
        resources.getString(R.string.notification_countdown),
        NotificationManager.IMPORTANCE_DEFAULT,
    )

    init {
        notificationManager.createNotificationChannel(countDownNotificationChannel)
    }

    internal fun getCountDownNotification(
        context: Context,
        timeSince: Duration,
    ): CountDownhNotification {
        val builder: Notification.Builder = Notification.Builder(
            context,
            countDownNotificationChannel.id,
        )
            .setSmallIcon(R.drawable.ic_launcher_foreground) // TODO: is this right?
            .setWhen(timeSince.inWholeSeconds)
            .setOngoing(true)
            .setShowWhen(true)
            .setUsesChronometer(true)
            .setChronometerCountDown(true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            builder.setCategory(Notification.CATEGORY_STOPWATCH)
        }
        val built = builder.build()
        return CountDownhNotification(
            id = nextNotificationId(),
            notification = built,
        )
    }
}
