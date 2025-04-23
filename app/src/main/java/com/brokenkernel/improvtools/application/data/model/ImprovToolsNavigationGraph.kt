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
import com.brokenkernel.improvtools.suggestionGenerator.presentation.view.SuggestionsScreen
import com.brokenkernel.improvtools.timer.presentation.view.TimerScreen
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
                onLaunchTitleCallback = {
                    improvToolsAppState.setScreenTitleTo(
                        NavigableScreens.HelpAndAboutScreen.titleResource,
                    )
                },
            )
        }
        composable<NavigableRoute.SettingsRoute> {
            SettingsScreen(
                onLaunchTitleCallback = {
                    improvToolsAppState.setScreenTitleTo(
                        NavigableScreens.SettingsScreen.titleResource,
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
                    improvToolsAppState.setScreenTitleTo(
                        NavigableScreens.SuggestionGeneratorScreen.titleResource,
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
                    improvToolsAppState.setScreenTitleTo(
                        NavigableScreens.TimerScreen.titleResource,
                    )
                },

            )
        }
        composable<NavigableRoute.TipsAndAdviceRoute> {
            TipsAndAdviceScreen(
                onLaunchTitleCallback = {
                    improvToolsAppState.setScreenTitleTo(
                        NavigableScreens.TipsAndAdviceScreen.titleResource,
                    )
                },

            )
        }

        composable<NavigableRoute.PrivacyRoute> {
            PrivacyScreen(
                onLaunchTitleCallback = {
                    improvToolsAppState.setScreenTitleTo(
                        NavigableScreens.PrivacyScreen.titleResource,
                    )
                },
            )
        }

        composable<NavigableRoute.LibrariesRoute> {
            LibrariesScreen(
                onLaunchTitleCallback = {
                    improvToolsAppState.setScreenTitleTo(
                        NavigableScreens.LibrariesScreen.titleResource,
                    )
                },
            )
        }

        encyclopaediaPageDestinations(improvToolsAppState)
    }
}
