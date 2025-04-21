package com.brokenkernel.improvtools.encyclopaedia.presentation.view

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.brokenkernel.improvtools.application.data.model.ImprovToolsAppState
import com.brokenkernel.improvtools.application.data.model.NavigableRoute

internal fun NavGraphBuilder.encyclopaediaPageDestinations(improvToolsAppState: ImprovToolsAppState) {
    composable<NavigableRoute.GamesPageRoute> {
        GamesTab()
    }

    composable<NavigableRoute.PeoplePageRoute> {
        PeopleTab()
    }

    composable<NavigableRoute.EmotionPageRoute> {
        EmotionTab()
    }

    composable<NavigableRoute.ThesaurusPageRoute> {
        ThesaurusTabAllItems(improvToolsAppState = improvToolsAppState)
    }

    composable<NavigableRoute.ThesaurusWordRoute> { backStackEntry ->
        val route: NavigableRoute.ThesaurusWordRoute = backStackEntry.toRoute()
        ThesaurusTabSingleWord(route.word, improvToolsAppState)
    }
}
