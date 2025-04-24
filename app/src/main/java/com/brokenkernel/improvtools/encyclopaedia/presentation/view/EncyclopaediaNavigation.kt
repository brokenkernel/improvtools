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
                improvToolsAppState.setScaffoldData(
                    NavigableScreens.GamesPageScreen.titleResource,
                    newExtraMenu = null,
                )
            },
        )
    }

    composable<NavigableRoute.PeoplePageRoute> {
        PeopleTab(
            onLaunchTitleCallback = {
                improvToolsAppState.setScaffoldData(
                    NavigableScreens.PeoplePageScreen.titleResource,
                    newExtraMenu = null,
                )
            },
        )
    }

    composable<NavigableRoute.EmotionPageRoute> {
        EmotionTab(
            onLaunchTitleCallback = {
                improvToolsAppState.setScaffoldData(
                    NavigableScreens.EmotionsPageScreen.titleResource,
                    newExtraMenu = null,
                )
            },
        )
    }

    composable<NavigableRoute.ThesaurusPageRoute> {
        ThesaurusTabAllItems(
            improvToolsAppState = improvToolsAppState,
            onLaunchTitleCallback = {
                improvToolsAppState.setScaffoldData(
                    NavigableScreens.ThesaurusPageScreen.titleResource,
                    newExtraMenu = null,
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
                improvToolsAppState.setScaffoldData(
                    NavigableScreens.ThesaurusPageScreen.titleResource,
                    newExtraMenu = null,
                )
            },
        )
    }
}
