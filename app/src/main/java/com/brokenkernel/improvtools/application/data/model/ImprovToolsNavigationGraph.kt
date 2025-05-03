package com.brokenkernel.improvtools.application.data.model

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.compose.NavHost
import com.brokenkernel.improvtools.application.api.applicationRoutes
import com.brokenkernel.improvtools.encyclopaedia.api.encyclopaediaPageDestinations
import com.brokenkernel.improvtools.settings.api.settingsRoutes
import com.brokenkernel.improvtools.suggestionGenerator.api.suggestionsRoutes
import com.brokenkernel.improvtools.timer.api.timerRoutes
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.generated.NavGraphs
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
internal fun ImprovToolsNavigationGraph(
    improvToolsAppState: ImprovToolsAppState,
    initialRoute: NavigableRoute,
    destinationNavigableRoute: DestinationsNavigator,
) {
    val context = LocalContext.current
    DisposableEffect(improvToolsAppState.navController) {
        val listener = NavController.OnDestinationChangedListener { _, destination: NavDestination, _ ->
            val params = Bundle()
            params.putString(
                FirebaseAnalytics.Param.SCREEN_NAME,
                context.getString(
                    improvToolsAppState.currentTitle.value,
                ),
            )
            params.putString(FirebaseAnalytics.Param.SCREEN_CLASS, destination.label?.toString())
            Firebase.analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, params)
        }
        improvToolsAppState.navController.addOnDestinationChangedListener(listener)
        onDispose {
            improvToolsAppState.navController.removeOnDestinationChangedListener(listener)
        }
    }
    DestinationsNavHost(navGraph = NavGraphs.root)
    NavHost(
        navController = improvToolsAppState.navController,
        startDestination = initialRoute,
    ) {
        suggestionsRoutes(improvToolsAppState)
        settingsRoutes(improvToolsAppState)
        timerRoutes(improvToolsAppState)
        encyclopaediaPageDestinations(improvToolsAppState)
        applicationRoutes(improvToolsAppState)
    }
}
