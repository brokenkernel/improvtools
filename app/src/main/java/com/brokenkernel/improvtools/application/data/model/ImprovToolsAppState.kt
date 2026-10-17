package com.brokenkernel.improvtools.application.data.model

import androidx.annotation.UiThread
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.brokenkernel.improvtools.coreinfra.BottomSheetContent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class ImprovToolsAppState {
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
