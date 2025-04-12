package com.brokenkernel.improvtools.application.data.model

import android.os.Bundle
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@Stable
internal class ImprovToolsAppState(
    val drawerState: DrawerState,
    val navController: NavHostController,
    private val currentNavigableScreen: MutableStateFlow<NavigableScreens> = MutableStateFlow(NavigableScreens.SuggestionGenerator),
) {
//    // UI State
//    val currentDestination: NavDestination?
//        @Composable get() = navController
//            .currentBackStackEntryAsState().value?.destination

    fun currentNavigableScreenAsState(): StateFlow<NavigableScreens> {
        return currentNavigableScreen.asStateFlow()
    }

    fun navigateTo(dest: NavigableRoute) {
        val firebaseBundle = Bundle()
        // TODO: do I even need this anymore once I am fully on routes?
        firebaseBundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, dest.toString())
        firebaseBundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, dest::class.qualifiedName)
        Firebase.analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, firebaseBundle)
        Firebase.analytics.setDefaultEventParameters(firebaseBundle)
        currentNavigableScreen.value = routeToScreen(dest)
    }

}

@Composable
internal fun rememberImprovToolsAppState(
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    navController: NavHostController = rememberNavController(),
    currentNavigableScreen: NavigableScreens = remember { NavigableScreens.SuggestionGenerator },
): ImprovToolsAppState = remember(drawerState, navController, currentNavigableScreen) {
    ImprovToolsAppState(drawerState, navController, MutableStateFlow(currentNavigableScreen))
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