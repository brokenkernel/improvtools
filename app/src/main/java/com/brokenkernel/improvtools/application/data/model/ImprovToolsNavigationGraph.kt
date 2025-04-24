package com.brokenkernel.improvtools.application.data.model

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.brokenkernel.improvtools.application.presentation.view.AboutTab
import com.brokenkernel.improvtools.application.presentation.view.LibrariesTab
import com.brokenkernel.improvtools.application.presentation.view.PrivacyTab
import com.brokenkernel.improvtools.encyclopaedia.EncyclopaediaSectionNavigation
import com.brokenkernel.improvtools.encyclopaedia.presentation.view.encyclopaediaPageDestinations
import com.brokenkernel.improvtools.settings.presentation.view.SettingsTab
import com.brokenkernel.improvtools.settings.presentation.view.SuggestionsTabMenu
import com.brokenkernel.improvtools.suggestionGenerator.presentation.view.SuggestionsTab
import com.brokenkernel.improvtools.timer.presentation.view.TimerTab
import com.brokenkernel.improvtools.tipsandadvice.presentation.view.TipsAndAdviceTab
import com.brokenkernel.improvtools.tipsandadvice.presentation.view.TipsAndAdviceTabMenu

@Composable
internal fun ImprovToolsNavigationGraph(
    improvToolsAppState: ImprovToolsAppState,
    onNavigateToRoute: (NavigableRoute) -> Unit,
    initialRoute: NavigableRoute,
) {
    NavHost(
        navController = improvToolsAppState.navController,
        startDestination = initialRoute,
    ) {
        composable<NavigableRoute.HelpAndAboutRoute> {
            AboutTab(
                // TODO: specific navigator
                onNavigateToRoute = onNavigateToRoute,
                onLaunchCallback = {
                    improvToolsAppState.setScaffoldData(
                        newTitle = NavigableScreens.HelpAndAboutScreen.titleResource,
                        newExtraMenu = null,
                    )
                },
            )
        }
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
        composable<NavigableRoute.SuggestionGeneratorRoute> {
            SuggestionsTab(
                onNavigateToEmotionsInfographic = {
                    onNavigateToRoute(NavigableRoute.EmotionPageRoute)
                },
                onLaunchTitleCallback = {
                    improvToolsAppState.setScaffoldData(
                        newTitle = NavigableScreens.SuggestionGeneratorScreen.titleResource,
                        newExtraMenu = {
                            SuggestionsTabMenu(
                                expanded = improvToolsAppState.extraMenuExpandedState,
                                onDismiss = {
                                    improvToolsAppState.extraMenuExpandedState =
                                        !improvToolsAppState.extraMenuExpandedState
                                },
                            )
                        },
                    )
                },
                onNavigateToWord = {
                    EncyclopaediaSectionNavigation.navigateToThesaurusWord(improvToolsAppState, it)
                },
            )
        }
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

        encyclopaediaPageDestinations(improvToolsAppState)
    }
}
