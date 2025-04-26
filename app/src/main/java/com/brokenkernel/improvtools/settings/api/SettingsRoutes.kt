package com.brokenkernel.improvtools.settings.api

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.brokenkernel.improvtools.application.data.model.ImprovToolsAppState
import com.brokenkernel.improvtools.application.data.model.NavigableRoute
import com.brokenkernel.improvtools.application.data.model.NavigableScreens
import com.brokenkernel.improvtools.settings.presentation.view.SettingsTab

internal fun NavGraphBuilder.settingsRoutes(improvToolsAppState: ImprovToolsAppState) {
    composable<NavigableRoute.SettingsRoute> {
        SettingsTab(
            onLaunchTitleCallback = {
                improvToolsAppState.setScaffoldData(
                    newTitle = NavigableScreens.SettingsScreen.titleResource,
                    newExtraMenu = null,
                )
            },
        )
    }
}
