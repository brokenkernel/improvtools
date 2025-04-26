package com.brokenkernel.improvtools.application.data.model

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import com.brokenkernel.improvtools.application.api.applicationRoutes
import com.brokenkernel.improvtools.encyclopaedia.api.encyclopaediaPageDestinations
import com.brokenkernel.improvtools.settings.api.settingsRoutes
import com.brokenkernel.improvtools.suggestionGenerator.api.suggestionsRoutes
import com.brokenkernel.improvtools.timer.api.timerRoutes

@Composable
internal fun ImprovToolsNavigationGraph(
    improvToolsAppState: ImprovToolsAppState,
    initialRoute: NavigableRoute,
) {
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
