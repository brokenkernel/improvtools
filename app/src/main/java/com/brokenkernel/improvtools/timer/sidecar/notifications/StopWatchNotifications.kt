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
import com.brokenkernel.improvtools.timer.presentation.viewmodel.TimerState
import javax.inject.Inject
import kotlin.time.ExperimentalTime

internal data class StopWatchNotification(override val id: Int, override val underlying: Notification) :
    ImprovToolsNotification

@OptIn(ExperimentalTime::class)
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
        timer: TimerState,
    ): StopWatchNotification {
        val builder = getPartiallyBuiltNotification(context, stopWatchNotificationChannel.id, timer)
            .setChronometerCountDown(true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            builder.setCategory(Notification.CATEGORY_STOPWATCH)
        }
        val built = builder.build()
        return StopWatchNotification(
            id = nextNotificationId(),
            underlying = built,
        )
    }
}
