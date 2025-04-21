package com.brokenkernel.improvtools.application.data.model

import android.util.Log
import android.util.Log.INFO
import androidx.annotation.StringRes
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.brokenkernel.improvtools.TAG

@Stable
internal class ImprovToolsAppState(
    val drawerState: DrawerState,
    val navController: NavHostController,
    @param:StringRes val currentTitle: MutableState<Int>,
) {

    @Composable
    fun currentBackStackEntryAsState(): State<NavBackStackEntry?> {
        return navController.currentBackStackEntryAsState()
    }

    // TODO: consider moving this out to the screen rather than app state??
    @Composable
    fun amIOnScreen(screen: NavigableScreens): Boolean {
        val currentNavigableRoute: NavDestination? = currentBackStackEntryAsState().value?.destination

        return currentNavigableRoute?.hierarchy?.any { it ->
            screen.matchingRoutes.any { route ->
                it.hasRoute(route::class)
            }
        } == true
    }

    fun navigateTo(dest: NavigableRoute, @StringRes title: Int) {
        currentTitle.value = title
        if (Log.isLoggable(TAG, INFO)) {
            Log.i(TAG, "Navigating to $dest")
        }
        navController.navigate(dest) {
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateBackTo(dest: NavigableRoute, @StringRes title: Int) {
        currentTitle.value = title
        if (Log.isLoggable(TAG, INFO)) {
            Log.i(TAG, "Navigating BACK to $dest")
        }
        // popUpTo does not work for reasons I'm not sure. This seems to work for now...
        navController.popBackStack(inclusive = false, route = dest)
    }
}

@Composable
internal fun rememberImprovToolsAppState(
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    navController: NavHostController = rememberNavController(),
    @StringRes titleState: MutableState<Int>,
): ImprovToolsAppState = remember(drawerState, navController, titleState) {
    ImprovToolsAppState(drawerState, navController, titleState)
}
