package com.brokenkernel.improvtools.application.presentation.view

import androidx.compose.runtime.Composable
import com.brokenkernel.improvtools.application.navigation.ImprovToolsDestination
import com.brokenkernel.improvtools.buzzer.view.BuzzerTabInternal
import com.brokenkernel.improvtools.components.sidecar.navigation.ImprovToolsNavigationGraph

@ImprovToolsDestination<ImprovToolsNavigationGraph>
@Composable
internal fun BuzzerTab() {
    BuzzerTabInternal()
}
