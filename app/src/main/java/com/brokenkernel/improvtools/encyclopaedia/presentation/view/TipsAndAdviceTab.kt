package com.brokenkernel.improvtools.encyclopaedia.presentation.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.brokenkernel.improvtools.application.navigation.ImprovToolsDestination
import com.brokenkernel.improvtools.components.sidecar.navigation.ImprovToolsNavigationGraph
import com.brokenkernel.improvtools.encyclopaedia.android.tipsandadvice.view.TipsAndAdviceScreen
import com.brokenkernel.improvtools.encyclopaedia.presentation.viewmodel.TipsAndAdviceViewModel

@ImprovToolsDestination<ImprovToolsNavigationGraph>
@Composable
internal fun TipsAndAdviceTab(
    viewModel: TipsAndAdviceViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    TipsAndAdviceScreen(uiState)
}
