package com.brokenkernel.improvtools.application.presentation.view

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.brokenkernel.improvtools.application.data.model.NavigableScreens
import com.brokenkernel.improvtools.settings.presentation.view.SettingsScreen
import com.brokenkernel.improvtools.suggestionGenerator.presentation.view.SuggestionsScreen

@Composable
fun DrawerNavGraph(drawerNavController: NavHostController) {
    NavHost(
        navController = drawerNavController,
        startDestination = NavigableScreens.SuggestionGenerator.route
    ) {
        composable(NavigableScreens.SuggestionGenerator.route) {
            SuggestionsScreen()
        }

        composable(NavigableScreens.About.route) {
            AboutScreen()
        }
        composable(NavigableScreens.Settings.route) {
            SettingsScreen()
        }
    }
}