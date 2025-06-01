package com.brokenkernel.improvtools.encyclopaedia.presentation.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.brokenkernel.improvtools.application.navigation.ImprovToolsDestination
import com.brokenkernel.improvtools.components.sidecar.navigation.ImprovToolsNavigationGraph
import com.brokenkernel.improvtools.encyclopaedia.android.tipsandadvice.TipsAndAdviceScreenAsList
import com.brokenkernel.improvtools.encyclopaedia.android.tipsandadvice.TipsAndAdviceScreenAsSwipable
import com.brokenkernel.improvtools.encyclopaedia.data.model.TipsAndAdviceViewModeUI
import com.brokenkernel.improvtools.encyclopaedia.data.tipsandadvice.TipsAndAdviceUIState
import com.brokenkernel.improvtools.encyclopaedia.presentation.viewmodel.TipsAndAdviceViewModel

@Composable
public fun TipsAndAdviceAsSelectable(taaViewMode: TipsAndAdviceViewModeUI, uiState: TipsAndAdviceUIState) {
    when (taaViewMode) {
        TipsAndAdviceViewModeUI.SWIPEABLE -> TipsAndAdviceScreenAsSwipable(uiState)
        TipsAndAdviceViewModeUI.LIST -> TipsAndAdviceScreenAsList(uiState)
    }
}

@ImprovToolsDestination<ImprovToolsNavigationGraph>
@Composable
internal fun TipsAndAdviceTab(
    viewModel: TipsAndAdviceViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val taaViewMode by viewModel.taaViewMode.collectAsStateWithLifecycle()
    TipsAndAdviceAsSelectable(taaViewMode, uiState)
}
