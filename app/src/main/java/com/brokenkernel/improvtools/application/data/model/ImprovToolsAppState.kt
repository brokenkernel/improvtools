package com.brokenkernel.improvtools.application.data.model

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Stable
internal class ImprovToolsAppState(
    val drawerState: DrawerState,
    val navController: NavHostController,
    val _currentNavigableScreen_unused: NavigableScreens,
) {
    // UI State
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination
}

@Composable
internal fun rememberImprovToolsAppState(
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    navController: NavHostController = rememberNavController(),
    currentNavigableScreen: NavigableScreens = NavigableScreens.SuggestionGenerator,
): ImprovToolsAppState = remember(drawerState, navController) {
    ImprovToolsAppState(drawerState, navController, currentNavigableScreen)
}


//    drawerNavController.addOnDestinationChangedListener {
//            controller: NavController,
//            destination: NavDestination,
//            args: Bundle?,
//        ->
//        val whichScreen: NavigableScreens? = NavigableScreens.byRoute(destination.route)
//        if (whichScreen != null) {
//            currentNavigableScreen = whichScreen
//        }
//    }