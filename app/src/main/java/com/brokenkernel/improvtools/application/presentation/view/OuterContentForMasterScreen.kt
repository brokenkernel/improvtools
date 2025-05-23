package com.brokenkernel.improvtools.application.presentation.view

import androidx.compose.runtime.Composable
import com.brokenkernel.improvtools.application.data.model.NavigableScreens
import com.brokenkernel.improvtools.ui.theme.ImprovToolsTheme

@Composable
internal fun OuterContentForMasterScreen(
    initialScreen: NavigableScreens,
) {
    ImprovToolsTheme {
        ImprovToolsNavigationDrawer(
            initialScreen = initialScreen,
        )
    }
}
