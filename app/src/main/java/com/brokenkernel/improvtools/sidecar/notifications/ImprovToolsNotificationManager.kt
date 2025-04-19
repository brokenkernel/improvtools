package com.brokenkernel.improvtools.sidecar.notifications

import android.app.Notification
import android.app.NotificationManager

internal abstract class ImprovToolsNotification(
    val id: Int,
    val underlying: Notification,
)

internal abstract class ImprovToolsNotificationManager<T>(
    val notificationManager: NotificationManager,
)
    where T : ImprovToolsNotification {
    private var currentNotificationId: Int = 0

    protected fun nextNotificationId(): Int {
        currentNotificationId++
        return currentNotificationId
    }

    fun send(notification: T) {
        notificationManager.notify(notification.id, notification.underlying)
    }
}
