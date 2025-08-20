package com.brokenkernel.improvtools.application.navigation

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalResources
import com.brokenkernel.improvtools.application.data.model.NavigableScreens
import com.brokenkernel.improvtools.coreinfra.LaunchWrapper
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import com.ramcosta.composedestinations.scope.DestinationScope
import com.ramcosta.composedestinations.wrapper.DestinationWrapper

internal object LogFirebaseScreenViewWrapper : DestinationWrapper {

    @SuppressWarnings("ComposableLambdaParameterNaming")
    @Composable
    override fun <T> DestinationScope<T>.Wrap(screenContent: @Composable (() -> Unit)) {
        val navigableScreen: NavigableScreens = NavigableScreens.Companion.byRoute(destination)
        val resources = LocalResources.current

        val params = Bundle()
        params.putString(
            FirebaseAnalytics.Param.SCREEN_NAME,
            resources.getString(navigableScreen.titleResource),
        )
        params.putString(FirebaseAnalytics.Param.SCREEN_CLASS, destination.label?.toString())
        LaunchWrapper(
            onLaunchCallback = {
                Firebase.analytics.logEvent(
                    FirebaseAnalytics.Event.SCREEN_VIEW,
                    params,
                )
            },
        ) {
            screenContent()
        }
    }
}
