package com.brokenkernel.improvtools.application.data.model

import android.util.Log
import android.util.Log.INFO
import androidx.annotation.StringRes
import androidx.annotation.UiThread
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.brokenkernel.improvtools.TAG
import com.brokenkernel.improvtools.application.presentation.api.BottomSheetContent

internal class ImprovToolsAppState(
    val drawerState: DrawerState,
    val navController: NavHostController,
    @param:StringRes val currentTitle: MutableState<Int>, // todo: expose a non_mutable variant
    var extraMenu: MutableState<(@Composable () -> Unit)?> = mutableStateOf(null),
) {

    // TODO: this is somewhat passing state down instead of bubbling events up.
    // I should, instead, be continuously passing `onDismsiss callbacks or some such. I'll try that in the fuutre
    var extraMenuExpandedState: Boolean by mutableStateOf(false)

    var bottomSheetContent: BottomSheetContent? by mutableStateOf<BottomSheetContent?>(null)
        private set

    @UiThread
    fun setBottomSheetTo(newContent: BottomSheetContent?) {
        bottomSheetContent = newContent
    }

    fun setScaffoldData(
        @StringRes newTitle: Int,
        newExtraMenu: (@Composable () -> Unit)?,
    ) {
        this.currentTitle.value = newTitle
        extraMenu.value = newExtraMenu
    }

    @Composable
    fun currentBackStackEntryAsState(): State<NavBackStackEntry?> {
        return navController.currentBackStackEntryAsState()
    }

    // TODO: consider moving this out to the screen rather than app state??
    @Composable
    fun amIOnScreen(screen: NavigableScreens): Boolean {
        val currentNavigableRoute: NavDestination? = currentBackStackEntryAsState().value?.destination

        return currentNavigableRoute?.hierarchy?.any { it ->
            it.hasRoute(screen.matchingRoute::class)
        } == true
    }

    @UiThread
    fun navigateTo(dest: NavigableRoute) {
        if (Log.isLoggable(TAG, INFO)) {
            Log.i(TAG, "Navigating to $dest")
        }
        navController.navigate(dest) {
            launchSingleTop = true
            restoreState = true
        }
    }

    @UiThread
    fun navigateBackTo(dest: NavigableRoute) {
        if (Log.isLoggable(TAG, INFO)) {
            Log.i(TAG, "Navigating BACK to $dest")
        }
        // popUpTo does not work for reasons I'm not sure. This seems to work for now...
        navController.popBackStack(inclusive = false, route = dest)
    }

    @UiThread
    fun navigateBack() {
        if (Log.isLoggable(TAG, INFO)) {
            Log.i(TAG, "Navigating BACK")
        }
        navController.popBackStack()
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
