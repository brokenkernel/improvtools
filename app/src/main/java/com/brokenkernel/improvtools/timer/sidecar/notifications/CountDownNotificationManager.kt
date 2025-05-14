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

internal class CountDownhNotification(
    override val id: Int,
    override val underlying: Notification,
) :
    ImprovToolsNotification

internal class CountDownNotificationManager @Inject constructor(
    notificationManager: NotificationManager,
    resources: Resources,
) : ImprovToolsNotificationManager<CountDownhNotification>(notificationManager) {
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
        timer: TimerState,
    ): CountDownhNotification {
        val builder = getPartiallyBuiltNotification(context, countDownNotificationChannel.id, timer)
            .setChronometerCountDown(true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            builder.setCategory(Notification.CATEGORY_STOPWATCH)
        }
        val built = builder.build()
        return CountDownhNotification(
            id = nextNotificationId(),
            underlying = built,
        )
    }
}
