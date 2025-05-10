package com.brokenkernel.improvtools.application.data.model

import android.util.Log
import android.util.Log.INFO
import androidx.annotation.StringRes
import androidx.annotation.UiThread
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class ImprovToolsAppState(
    @StringRes initialTitle: Int,
    val drawerState: DrawerState,
    val navController: NavHostController,
    val navigator: DestinationsNavigator,
) {

    @StringRes
    private val _currentTitle: MutableStateFlow<Int> = MutableStateFlow(initialTitle)
    val currentTitle: StateFlow<Int> = _currentTitle.asStateFlow()

    private val _extraMenu: MutableStateFlow<(@Composable () -> Unit)?> = MutableStateFlow(null)
    val extraMenu: StateFlow<@Composable (() -> Unit)?> = _extraMenu.asStateFlow()

    // TODO: this is passing state down instead of bubbling events up.
    // I should, instead, be continuously passing `onDismiss callbacks or some such. I'll try that in the future
    var extraMenuExpandedState: Boolean by mutableStateOf(false)

    private val _bottomSheetContent: MutableStateFlow<BottomSheetContent?> = MutableStateFlow<BottomSheetContent?>(null)
    var bottomSheetContent: StateFlow<BottomSheetContent?> = _bottomSheetContent.asStateFlow()

    @UiThread
    fun setBottomSheetTo(newContent: BottomSheetContent?) {
        _bottomSheetContent.value = newContent
    }

    fun setScaffoldData(
        @StringRes newTitle: Int,
        newExtraMenu: (@Composable () -> Unit)?,
    ) {
        this._currentTitle.value = newTitle
        _extraMenu.value = newExtraMenu
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
    @StringRes initialTitle: Int,
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    navController: NavHostController = rememberNavController(),
    navigator: DestinationsNavigator = navController.rememberDestinationsNavigator(),
): ImprovToolsAppState = remember(drawerState, navController, initialTitle, navigator) {
    ImprovToolsAppState(initialTitle, drawerState, navController, navigator)
}
