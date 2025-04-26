package com.brokenkernel.improvtools.timer.api

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.brokenkernel.improvtools.application.data.model.ImprovToolsAppState
import com.brokenkernel.improvtools.application.data.model.NavigableRoute
import com.brokenkernel.improvtools.application.data.model.NavigableScreens
import com.brokenkernel.improvtools.timer.presentation.view.TimerTab

internal fun NavGraphBuilder.timerRoutes(improvToolsAppState: ImprovToolsAppState) {
    composable<NavigableRoute.TimerRoute> {
        TimerTab(
            onLaunchTitleCallback = {
                improvToolsAppState.setScaffoldData(
                    NavigableScreens.TimerScreen.titleResource,
                    newExtraMenu = null,
                )
            },
        )
    }
}
