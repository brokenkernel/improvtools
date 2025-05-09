package com.brokenkernel.improvtools.application.api

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.brokenkernel.improvtools.application.data.model.ImprovToolsAppState
import com.brokenkernel.improvtools.application.data.model.NavigableRoute
import com.brokenkernel.improvtools.application.data.model.NavigableScreens
import com.brokenkernel.improvtools.application.presentation.view.AboutTab
import com.brokenkernel.improvtools.application.presentation.view.LaunchWrapper
import com.brokenkernel.improvtools.application.presentation.view.LibrariesTab
import com.brokenkernel.improvtools.application.presentation.view.PrivacyTab

internal fun NavGraphBuilder.applicationRoutes(improvToolsAppState: ImprovToolsAppState) {
    composable<NavigableRoute.HelpAndAboutRoute> {
        LaunchWrapper(
            onLaunchCallback = {
                improvToolsAppState.setScaffoldData(
                    newTitle = NavigableScreens.HelpAndAboutScreen.titleResource,
                    newExtraMenu = null,
                )
            },
        ) {
            AboutTab(
                onNavigateToPrivacyScreen = { improvToolsAppState.navigateTo(NavigableRoute.PrivacyRoute) },
            )
        }
    }

    composable<NavigableRoute.PrivacyRoute> {
        LaunchWrapper(
            onLaunchCallback = {
                improvToolsAppState.setScaffoldData(
                    NavigableScreens.PrivacyScreen.titleResource,
                    newExtraMenu = null,
                )
            },
        ) {
            PrivacyTab()
        }
    }

    composable<NavigableRoute.LibrariesRoute> {
        LaunchWrapper(
            onLaunchCallback = {
                improvToolsAppState.setScaffoldData(
                    NavigableScreens.LibrariesScreen.titleResource,
                    newExtraMenu = null,
                )
            },
        ) {
            LibrariesTab()
        }
    }
}
