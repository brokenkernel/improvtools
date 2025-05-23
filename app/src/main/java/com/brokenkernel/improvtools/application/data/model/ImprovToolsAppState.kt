package com.brokenkernel.improvtools.application.data.model

import androidx.annotation.StringRes
import androidx.annotation.UiThread
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.brokenkernel.improvtools.application.presentation.api.BottomSheetContent
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.utils.currentDestinationAsState
import com.ramcosta.composedestinations.utils.toDestinationsNavigator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class ImprovToolsAppState(
    @param:StringRes private val initialTitle: Int,
    val navController: NavHostController,
) {

    val navigator: DestinationsNavigator = navController.toDestinationsNavigator()

    @StringRes
    private val _currentTitle: MutableStateFlow<Int> = MutableStateFlow(initialTitle)
    val currentTitle: StateFlow<Int> = _currentTitle.asStateFlow()

    private val _extraMenu: MutableStateFlow<@Composable ((ImprovToolsAppState) -> Unit)?> = MutableStateFlow(null)
    val extraMenu: StateFlow<@Composable ((ImprovToolsAppState) -> Unit)?> = _extraMenu.asStateFlow()

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
        newExtraMenu: @Composable ((ImprovToolsAppState) -> Unit)?,
    ) {
        this._currentTitle.value = newTitle
        _extraMenu.value = newExtraMenu
    }

    @Composable
    fun currentDestinationAsState(): State<DestinationSpec?> {
        return navController.currentDestinationAsState()
    }
}

@Composable
internal fun rememberImprovToolsAppState(
    @StringRes initialTitle: Int,
    navController: NavHostController = rememberNavController(),
): ImprovToolsAppState = remember(navController, initialTitle) {
    ImprovToolsAppState(initialTitle, navController)
}
