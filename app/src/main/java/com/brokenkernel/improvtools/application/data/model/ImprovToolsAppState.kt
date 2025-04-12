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
    private val currentNavigableRoute: MutableStateFlow<NavigableRoute> = MutableStateFlow(NavigableRoute.SuggestionGeneratorRoute),
) {
//    // UI State
//    val currentDestination: NavDestination?
//        @Composable get() = navController
//            .currentBackStackEntryAsState().value?.destination

    fun currentNavigableRouteAsState(): StateFlow<NavigableRoute> {
        return currentNavigableRoute.asStateFlow()
    }

    fun navigateTo(dest: NavigableRoute) {
        val firebaseBundle = Bundle()
        // TODO: do I even need this anymore once I am fully on routes?
        firebaseBundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, dest.toString())
        firebaseBundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, dest::class.qualifiedName)
        Firebase.analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, firebaseBundle)
        Firebase.analytics.setDefaultEventParameters(firebaseBundle)
        currentNavigableRoute.value = dest
    }

}

@Composable
internal fun rememberImprovToolsAppState(
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    navController: NavHostController = rememberNavController(),
    currentNavigableRoute: NavigableRoute = remember { NavigableRoute.SuggestionGeneratorRoute },
): ImprovToolsAppState = remember(drawerState, navController, currentNavigableRoute) {
    ImprovToolsAppState(drawerState, navController, MutableStateFlow(currentNavigableRoute))
}