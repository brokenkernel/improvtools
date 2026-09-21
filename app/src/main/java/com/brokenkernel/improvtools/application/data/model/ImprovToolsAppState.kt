package com.brokenkernel.improvtools.application.data.model

import androidx.annotation.UiThread
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.brokenkernel.improvtools.coreinfra.BottomSheetContent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class ImprovToolsAppState {

    private val _extraMenu: MutableStateFlow<@Composable ((ImprovToolsAppState) -> Unit)?> =
        MutableStateFlow(null)
    val extraMenu: StateFlow<@Composable ((ImprovToolsAppState) -> Unit)?> =
        _extraMenu.asStateFlow()

    // TODO: this is passing state down instead of bubbling events up.
    // I should, instead, be continuously passing `onDismiss callbacks or some such. I'll try that in the future
    var extraMenuExpandedState: Boolean by mutableStateOf(false)

    private val _bottomSheetContent: MutableStateFlow<BottomSheetContent?> = MutableStateFlow(null)
    var bottomSheetContent: StateFlow<BottomSheetContent?> = _bottomSheetContent.asStateFlow()

    @UiThread
    fun setBottomSheetTo(newContent: BottomSheetContent?) {
        _bottomSheetContent.value = newContent
    }
}

@Composable
internal fun rememberImprovToolsAppState(): ImprovToolsAppState = remember {
    ImprovToolsAppState()
}
