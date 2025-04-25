package com.brokenkernel.improvtools.application.presentation.view

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.brokenkernel.improvtools.application.data.model.ImprovToolsAppState
import com.brokenkernel.improvtools.application.data.model.NavigableRoute
import com.brokenkernel.improvtools.ui.theme.ImprovToolsTheme

@Composable
internal fun OuterContentForMasterScreen(
    improvToolsState: ImprovToolsAppState,
    initialRoute: NavigableRoute,
) {
    ImprovToolsTheme {
        Surface {
            ImprovToolsNavigationDrawer(
                improvToolsState,
                drawerState = improvToolsState.drawerState,
                initialRoute = initialRoute,
            )
        }
    }
}
