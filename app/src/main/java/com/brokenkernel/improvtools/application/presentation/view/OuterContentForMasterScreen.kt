package com.brokenkernel.improvtools.application.presentation.view

import androidx.compose.runtime.Composable
import com.brokenkernel.improvtools.application.data.model.ImprovToolsAppState
import com.brokenkernel.improvtools.ui.theme.ImprovToolsTheme
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec

@Composable
internal fun OuterContentForMasterScreen(
    improvToolsState: ImprovToolsAppState,
    initialRoute: DirectionDestinationSpec,
) {
    ImprovToolsTheme {
        ImprovToolsNavigationDrawer(
            improvToolsState,
            // TODO avoid passing down state
            drawerState = improvToolsState.drawerState,
            initialRoute = initialRoute,
        )
    }
}
