package com.brokenkernel.improvtools.application.presentation.view

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.brokenkernel.improvtools.application.data.model.NavigableRoute
import com.brokenkernel.improvtools.encyclopaedia.presentation.view.encyclopaediaPageDestinations
import com.brokenkernel.improvtools.settings.presentation.view.SettingsScreen
import com.brokenkernel.improvtools.suggestionGenerator.presentation.view.SuggestionsScreen
import com.brokenkernel.improvtools.timer.presentation.view.TimerScreen
import com.brokenkernel.improvtools.tipsandadvice.presentation.view.TipsAndAdviceScreen
import com.brokenkernel.improvtools.workshopgenerator.presentation.view.WorkshopGeneratorScreen

@Composable
internal fun DrawerNavGraph(
    navController: NavHostController,
    onNavigateToRoute: (NavigableRoute) -> Unit,
    initialRoute: NavigableRoute,
) {

    NavHost(
        navController = navController,
        startDestination = initialRoute,
    ) {
        composable<NavigableRoute.HelpAndAboutRoute> {
            AboutScreen()
        }
        composable<NavigableRoute.SettingsRoute> {
            SettingsScreen()
        }
        composable<NavigableRoute.SuggestionGeneratorRoute> {
            SuggestionsScreen(
                onNavigateToRoute = onNavigateToRoute,
            )
        }
        composable<NavigableRoute.TimerRoute> {
            TimerScreen()
        }
        composable<NavigableRoute.TipsAndAdviceRoute> {
            TipsAndAdviceScreen()
        }
        composable<NavigableRoute.WorkshopGeneratorRoute> {
            WorkshopGeneratorScreen()
        }
        encyclopaediaPageDestinations()
    }
}