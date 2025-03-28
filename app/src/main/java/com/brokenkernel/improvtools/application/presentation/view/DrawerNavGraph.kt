package com.brokenkernel.improvtools.application.presentation.view

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.brokenkernel.improvtools.application.data.model.NavigableActivities
import com.brokenkernel.improvtools.settings.presentation.view.SettingsScreen
import com.brokenkernel.improvtools.suggestionGenerator.presentation.view.SuggestionsScreen

@Composable
fun DrawerNavGraph(drawerNavController: NavHostController) {
    NavHost(
        navController = drawerNavController,
        startDestination = NavigableActivities.SuggestionGenerator.route
    ) {
        composable(NavigableActivities.SuggestionGenerator.route) {
            SuggestionsScreen()
        }

        composable(NavigableActivities.About.route) {
            AboutScreen()
        }
        composable(NavigableActivities.Settings.route) {
            SettingsScreen()
        }
    }
}