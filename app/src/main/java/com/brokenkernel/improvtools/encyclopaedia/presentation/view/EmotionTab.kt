package com.brokenkernel.improvtools.encyclopaedia.presentation.view

import androidx.compose.runtime.Composable
import com.brokenkernel.improvtools.application.navigation.ImprovToolsDestination
import com.brokenkernel.improvtools.components.sidecar.navigation.ImprovToolsNavigationGraph
import com.brokenkernel.improvtools.coreinfra.preview.PreviewsImprovToolsAll
import com.brokenkernel.improvtools.encyclopaedia.android.emotions.EmotionsTabInternal

@ImprovToolsDestination<ImprovToolsNavigationGraph>
@Composable
internal fun EmotionTab() {
    EmotionsTabInternal()
}

@PreviewsImprovToolsAll
@Composable
internal fun EmotionTabPreview() {
    EmotionTab()
}
