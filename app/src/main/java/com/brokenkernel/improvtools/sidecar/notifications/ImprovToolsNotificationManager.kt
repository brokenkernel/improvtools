package com.brokenkernel.improvtools.sidecar.notifications

import android.Manifest
import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresPermission
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.TAG
import com.brokenkernel.improvtools.timer.model.TimerState
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

internal interface ImprovToolsNotification {
    val id: Int
    val underlying: Notification
}

@OptIn(ExperimentalTime::class)
internal abstract class ImprovToolsNotificationManager<T>(
    val notificationManager: NotificationManager,
)
    where T : ImprovToolsNotification {
    private var currentNotificationId: Int = 1

    protected fun nextNotificationId(): Int {
        currentNotificationId++
        return currentNotificationId
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun send(notification: T) {
        Log.w(TAG, "trueSendStopwatchNotification: Enabled:" + notificationManager.areNotificationsEnabled())
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Log.w(TAG, "trueSendStopwatchNotification: Paused:" + notificationManager.areNotificationsPaused())
        }
        notificationManager.notify(notification.id, notification.underlying)
    }

    protected fun getPartiallyBuiltNotification(context: Context, channelID: String, timer: TimerState): Notification.Builder {
        // TODO: this is entirely wrong, and will actually be related to `TimerState`. Also need to deal with paused, etc. etc.
        val now: Instant = Clock.System.now()
        val builder = Notification.Builder(
            context,
            channelID,
        ).setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(timer.title)
            .setContentText(timer.title)
            .setWhen(now.toEpochMilliseconds()) // TODO: wrong, and shouldn't be passed,
            .setOngoing(true)
            .setShowWhen(true)
            .setUsesChronometer(true)
        return builder
    }
}
