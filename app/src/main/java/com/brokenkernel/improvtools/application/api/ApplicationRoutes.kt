package com.brokenkernel.improvtools.application.api

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.brokenkernel.improvtools.application.data.model.ImprovToolsAppState
import com.brokenkernel.improvtools.application.data.model.NavigableRoute
import com.brokenkernel.improvtools.application.data.model.NavigableScreens
import com.brokenkernel.improvtools.application.presentation.view.AboutTab
import com.brokenkernel.improvtools.application.presentation.view.LibrariesTab
import com.brokenkernel.improvtools.application.presentation.view.PrivacyTab

internal fun NavGraphBuilder.applicationRoutes(improvToolsAppState: ImprovToolsAppState) {
    composable<NavigableRoute.HelpAndAboutRoute> {
        AboutTab(
            onNavigateToPrivacyScreen = { improvToolsAppState.navigateTo(NavigableRoute.PrivacyRoute) },
            onLaunchTitleCallback = {
                improvToolsAppState.setScaffoldData(
                    newTitle = NavigableScreens.HelpAndAboutScreen.titleResource,
                    newExtraMenu = null,
                )
            },
        )
    }

    composable<NavigableRoute.PrivacyRoute> {
        PrivacyTab(
            onLaunchTitleCallback = {
                improvToolsAppState.setScaffoldData(
                    NavigableScreens.PrivacyScreen.titleResource,
                    newExtraMenu = null,
                )
            },
        )
    }

    composable<NavigableRoute.LibrariesRoute> {
        LibrariesTab(
            onLaunchTitleCallback = {
                improvToolsAppState.setScaffoldData(
                    NavigableScreens.LibrariesScreen.titleResource,
                    newExtraMenu = null,
                )
            },
        )
    }
}
