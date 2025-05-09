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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.brokenkernel.improvtools.TAG
import com.brokenkernel.improvtools.application.presentation.api.BottomSheetContent
import com.ramcosta.composedestinations.generated.navgraphs.ImprovToolsNavigationNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.utils.currentDestinationAsState
import com.ramcosta.composedestinations.utils.rememberDestinationsNavigator
import com.ramcosta.composedestinations.utils.startDestination

internal class ImprovToolsAppState(
    val drawerState: DrawerState,
    val navController: NavHostController,
    @param:StringRes val currentTitle: MutableState<Int>, // todo: expose a non_mutable variant
    val navigator: DestinationsNavigator,
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

    // TODO: wrapper
    fun setScaffoldData(
        @StringRes newTitle: Int,
        newExtraMenu: (@Composable () -> Unit)?,
    ) {
        this.currentTitle.value = newTitle
        extraMenu.value = newExtraMenu
    }

    // TODO: consider moving this out to the screen rather than app state??
    @Composable
    fun amIOnScreen(screen: NavigableScreens): Boolean {
        val currentDestination: DestinationSpec = navController.currentDestinationAsState().value
            ?: ImprovToolsNavigationNavGraph.startDestination
        return currentDestination == screen.matchingRoute
    }

    @UiThread
    fun navigateBack() {
        if (Log.isLoggable(TAG, INFO)) {
            Log.i(TAG, "Navigating BACK")
        }

        navigator.popBackStack()
    }
}

@Composable
internal fun rememberImprovToolsAppState(
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    navController: NavHostController = rememberNavController(),
    @StringRes titleState: MutableState<Int>,
    navigator: DestinationsNavigator = navController.rememberDestinationsNavigator(),
): ImprovToolsAppState = remember(drawerState, navController, titleState, navigator) {
    ImprovToolsAppState(drawerState, navController, titleState, navigator)
}
