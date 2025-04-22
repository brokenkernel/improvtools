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
            )
        }
        composable<NavigableRoute.SettingsRoute> {
            SettingsScreen()
        }
        composable<NavigableRoute.SuggestionGeneratorRoute> {
            SuggestionsScreen(
                onNavigateToEmotionsInfographic = {
                    onNavigateToRoute(NavigableRoute.EmotionPageRoute)
                },
                onNavigateToWord = {
                    EncyclopaediaSectionNavigation.navigateToThesaurusWord(improvToolsAppState, it)
                },
            )
        }
        composable<NavigableRoute.TimerRoute> {
            TimerScreen()
        }
        composable<NavigableRoute.TipsAndAdviceRoute> {
            TipsAndAdviceScreen()
        }

        composable<NavigableRoute.PrivacyRoute> {
            PrivacyScreen()
        }

        composable<NavigableRoute.LibrariesRoute> {
            LibrariesScreen()
        }

        encyclopaediaPageDestinations(improvToolsAppState)
    }
}
