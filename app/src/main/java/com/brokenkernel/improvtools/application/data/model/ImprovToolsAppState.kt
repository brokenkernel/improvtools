package com.brokenkernel.improvtools.application.data.model

import android.util.Log
import android.util.Log.INFO
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.brokenkernel.improvtools.TAG

@Stable
internal class ImprovToolsAppState(
    val drawerState: DrawerState,
    val navController: NavHostController,
) {

    @Composable
    fun currentBackStackEntryAsState(): State<NavBackStackEntry?> {
        return navController.currentBackStackEntryAsState()
    }

    fun navigateTo(dest: NavigableRoute) {
        if (Log.isLoggable(TAG, INFO)) {
            Log.i(TAG, "Navigating to $dest")
        }
        navController.navigate(dest) {
            launchSingleTop = true
            restoreState = true
        }
    }
}

@Composable
internal fun rememberImprovToolsAppState(
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    navController: NavHostController = rememberNavController(),
): ImprovToolsAppState = remember(drawerState, navController) {
    ImprovToolsAppState(drawerState, navController)
}