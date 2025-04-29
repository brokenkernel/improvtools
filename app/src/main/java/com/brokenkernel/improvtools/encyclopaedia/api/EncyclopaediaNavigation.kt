package com.brokenkernel.improvtools.encyclopaedia.api

import androidx.annotation.StringRes
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.brokenkernel.improvtools.application.data.model.ImprovToolsAppState
import com.brokenkernel.improvtools.application.data.model.NavigableRoute
import com.brokenkernel.improvtools.application.data.model.NavigableScreens
import com.brokenkernel.improvtools.encyclopaedia.presentation.view.EmotionTab
import com.brokenkernel.improvtools.encyclopaedia.presentation.view.GamesTab
import com.brokenkernel.improvtools.encyclopaedia.presentation.view.GlossaryTab
import com.brokenkernel.improvtools.encyclopaedia.presentation.view.PeopleTab
import com.brokenkernel.improvtools.encyclopaedia.presentation.view.ThesaurusTabAllItems
import com.brokenkernel.improvtools.encyclopaedia.presentation.view.ThesaurusTabSingleWord
import com.brokenkernel.improvtools.encyclopaedia.presentation.view.TipsAndAdviceTab
import com.brokenkernel.improvtools.encyclopaedia.presentation.view.TipsAndAdviceTabMenu
import com.brokenkernel.improvtools.encyclopaedia.presentation.viewmodel.ThesaurusSingleItemViewModel

internal fun NavGraphBuilder.encyclopaediaPageDestinations(improvToolsAppState: ImprovToolsAppState) {
    composable<NavigableRoute.TipsAndAdviceRoute> {
        TipsAndAdviceTab(
            onLaunchTitleCallback = {
                improvToolsAppState.setScaffoldData(
                    NavigableScreens.TipsAndAdviceScreen.titleResource,
                    newExtraMenu = null,
                )
                improvToolsAppState.extraMenu.value = {
                    TipsAndAdviceTabMenu(
                        expanded = improvToolsAppState.extraMenuExpandedState,
                        onDismiss = {
                            improvToolsAppState.extraMenuExpandedState = !improvToolsAppState.extraMenuExpandedState
                        },
                    )
                }
            },
        )
    }
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

    composable<NavigableRoute.GlossaryRoute> {
        GlossaryTab(
            onLaunchTitleCallback = {
                improvToolsAppState.setScaffoldData(
                    NavigableScreens.GlossaryPageScreen.titleResource,
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

        @StringRes val priorTitleResource: Int = rememberSaveable { improvToolsAppState.currentTitle.value }

        val viewModel = hiltViewModel<ThesaurusSingleItemViewModel, ThesaurusSingleItemViewModel.Factory>(
            creationCallback = { factory ->
                factory.create(word = route.word)
            },
        )

        ThesaurusTabSingleWord(
            viewModel = viewModel,
            onNavigateBack = { improvToolsAppState.navigateBack() },
            onLaunchTitleCallback = {
                improvToolsAppState.setScaffoldData(
                    NavigableScreens.ThesaurusPageScreen.titleResource,
                    newExtraMenu = null,
                )
            },
            priorTitleResource = priorTitleResource,
        )
    }
}
