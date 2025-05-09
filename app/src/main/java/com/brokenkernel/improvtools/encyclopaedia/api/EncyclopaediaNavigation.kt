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
import com.brokenkernel.improvtools.application.presentation.view.LaunchWrapper
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
        LaunchWrapper(
            onLaunchCallback = {
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
        ) {
            TipsAndAdviceTab()
        }
    }
    composable<NavigableRoute.GamesPageRoute> {
        LaunchWrapper(
            onLaunchCallback = {
                improvToolsAppState.setScaffoldData(
                    NavigableScreens.GamesPageScreen.titleResource,
                    newExtraMenu = null,
                )
            },
        ) {
            GamesTab()
        }
    }

    composable<NavigableRoute.PeoplePageRoute> {
        LaunchWrapper(
            onLaunchCallback = {
                improvToolsAppState.setScaffoldData(
                    NavigableScreens.PeoplePageScreen.titleResource,
                    newExtraMenu = null,
                )
            },
        ) {
            PeopleTab()
        }
    }

    composable<NavigableRoute.EmotionPageRoute> {
        LaunchWrapper(
            onLaunchCallback = {
                improvToolsAppState.setScaffoldData(
                    NavigableScreens.EmotionsPageScreen.titleResource,
                    newExtraMenu = null,
                )
            },
        ) {
            EmotionTab()
        }
    }

    composable<NavigableRoute.GlossaryRoute> {
        LaunchWrapper(
            onLaunchCallback = {
                improvToolsAppState.setScaffoldData(
                    NavigableScreens.GlossaryPageScreen.titleResource,
                    newExtraMenu = null,
                )
            },
        ) {
            GlossaryTab()
        }
    }

    composable<NavigableRoute.ThesaurusPageRoute> {
        LaunchWrapper(
            onLaunchCallback = {
                improvToolsAppState.setScaffoldData(
                    NavigableScreens.ThesaurusPageScreen.titleResource,
                    newExtraMenu = null,
                )
            },
        ) {
            ThesaurusTabAllItems(
                improvToolsAppState = improvToolsAppState,
            )
        }
    }

    composable<NavigableRoute.ThesaurusWordRoute> { backStackEntry ->
        val route: NavigableRoute.ThesaurusWordRoute = backStackEntry.toRoute()

        @StringRes val priorTitleResource: Int = rememberSaveable { improvToolsAppState.currentTitle.value }

        val viewModel = hiltViewModel<ThesaurusSingleItemViewModel, ThesaurusSingleItemViewModel.Factory>(
            creationCallback = { factory ->
                factory.create(word = route.word)
            },
        )

        LaunchWrapper(
            onLaunchCallback = {
                improvToolsAppState.setScaffoldData(
                    NavigableScreens.ThesaurusPageScreen.titleResource,
                    newExtraMenu = null,
                )
            },
        ) {
            ThesaurusTabSingleWord(
                viewModel = viewModel,
                onNavigateBack = { improvToolsAppState.navigateBack() },
                priorTitleResource = priorTitleResource,
            )
        }
    }
}
