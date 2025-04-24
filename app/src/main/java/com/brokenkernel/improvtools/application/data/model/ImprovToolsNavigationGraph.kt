package com.brokenkernel.improvtools.application.data.model

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.brokenkernel.improvtools.application.presentation.view.AboutScreen
import com.brokenkernel.improvtools.application.presentation.view.LibrariesScreen
import com.brokenkernel.improvtools.application.presentation.view.PrivacyScreen
import com.brokenkernel.improvtools.encyclopaedia.EncyclopaediaSectionNavigation
import com.brokenkernel.improvtools.encyclopaedia.presentation.view.encyclopaediaPageDestinations
import com.brokenkernel.improvtools.settings.presentation.view.SettingsScreen
import com.brokenkernel.improvtools.settings.presentation.view.SuggestionsScreenMenu
import com.brokenkernel.improvtools.suggestionGenerator.presentation.view.SuggestionsScreen
import com.brokenkernel.improvtools.timer.presentation.view.TimerScreen
import com.brokenkernel.improvtools.tipsandadvice.presentation.view.TipsAndAdviceMenu
import com.brokenkernel.improvtools.tipsandadvice.presentation.view.TipsAndAdviceScreen

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
            AboutScreen(
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
            SettingsScreen(
                onLaunchTitleCallback = {
                    improvToolsAppState.setScaffoldData(
                        newTitle = NavigableScreens.SettingsScreen.titleResource,
                        newExtraMenu = null,
                    )
                },
            )
        }
        composable<NavigableRoute.SuggestionGeneratorRoute> {
            SuggestionsScreen(
                onNavigateToEmotionsInfographic = {
                    onNavigateToRoute(NavigableRoute.EmotionPageRoute)
                },
                onLaunchTitleCallback = {
                    improvToolsAppState.setScaffoldData(
                        newTitle = NavigableScreens.SuggestionGeneratorScreen.titleResource,
                        newExtraMenu = {
                            SuggestionsScreenMenu(
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
            TimerScreen(
                onLaunchTitleCallback = {
                    improvToolsAppState.setScaffoldData(
                        NavigableScreens.TimerScreen.titleResource,
                        newExtraMenu = null,
                    )
                },
            )
        }
        composable<NavigableRoute.TipsAndAdviceRoute> {
            TipsAndAdviceScreen(
                onLaunchTitleCallback = {
                    improvToolsAppState.setScaffoldData(
                        NavigableScreens.TipsAndAdviceScreen.titleResource,
                        newExtraMenu = null,
                    )
                    improvToolsAppState.extraMenu.value = {
                        TipsAndAdviceMenu(
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
            PrivacyScreen(
                onLaunchTitleCallback = {
                    improvToolsAppState.setScaffoldData(
                        NavigableScreens.PrivacyScreen.titleResource,
                        newExtraMenu = null,
                    )
                },
            )
        }

        composable<NavigableRoute.LibrariesRoute> {
            LibrariesScreen(
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
