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

internal class StopWatchNotification(id: Int, notification: Notification) :
    ImprovToolsNotification(id, notification)

internal class StopWatchNotificationManager @Inject constructor(
    notificationManager: NotificationManager,
    resources: Resources,
) : ImprovToolsNotificationManager<StopWatchNotification>(notificationManager) {
    private val stopWatchNotificationChannel: NotificationChannel = NotificationChannel(
        "stopwatch",
        resources.getString(R.string.notification_stop_watches),
        NotificationManager.IMPORTANCE_DEFAULT,
    )

    init {
        notificationManager.createNotificationChannel(stopWatchNotificationChannel)
    }

    internal fun getStopWatchNotification(
        context: Context,
        timeSince: Duration,
    ): StopWatchNotification {
        val builder: Notification.Builder = Notification.Builder(
            context,
            stopWatchNotificationChannel.id,
        )
            .setSmallIcon(R.drawable.ic_launcher_foreground) // TODO: is this right?
            .setWhen(timeSince.inWholeSeconds)
            .setOngoing(true)
            .setShowWhen(true)
            .setUsesChronometer(true)
            .setChronometerCountDown(false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            builder.setCategory(Notification.CATEGORY_STOPWATCH)
        }
        val built = builder.build()
        return StopWatchNotification(
            id = nextNotificationId(),
            notification = built,
        )
    }
}
