package com.brokenkernel.improvtools.encyclopaedia.presentation.view

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.brokenkernel.improvtools.application.data.model.ImprovToolsAppState
import com.brokenkernel.improvtools.application.data.model.NavigableRoute
import com.brokenkernel.improvtools.application.data.model.NavigableScreens

internal fun NavGraphBuilder.encyclopaediaPageDestinations(improvToolsAppState: ImprovToolsAppState) {
    composable<NavigableRoute.GamesPageRoute> {
        GamesTab(
            onLaunchTitleCallback = {
                improvToolsAppState.setScreenTitleTo(
                    NavigableScreens.HelpAndAboutScreen.titleResource,
                )
            },
        )
    }

    composable<NavigableRoute.PeoplePageRoute> {
        PeopleTab(
            onLaunchTitleCallback = {
                improvToolsAppState.setScreenTitleTo(
                    NavigableScreens.PeoplePageScreen.titleResource,
                )
            },
        )
    }

    composable<NavigableRoute.EmotionPageRoute> {
        EmotionTab(
            onLaunchTitleCallback = {
                improvToolsAppState.setScreenTitleTo(
                    NavigableScreens.EmotionsPageScreen.titleResource,
                )
            },
        )
    }

    composable<NavigableRoute.ThesaurusPageRoute> {
        ThesaurusTabAllItems(
            improvToolsAppState = improvToolsAppState,
            onLaunchTitleCallback = {
                improvToolsAppState.setScreenTitleTo(
                    NavigableScreens.ThesaurusPageScreen.titleResource,
                )
            },
        )
    }

    composable<NavigableRoute.ThesaurusWordRoute> { backStackEntry ->
        val route: NavigableRoute.ThesaurusWordRoute = backStackEntry.toRoute()
        ThesaurusTabSingleWord(
            word = route.word,
            improvToolsAppState = improvToolsAppState,
            onLaunchTitleCallback = {
                improvToolsAppState.setScreenTitleTo(
                    NavigableScreens.ThesaurusPageScreen.titleResource,
                )
            },
        )
    }
}
