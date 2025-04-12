package com.brokenkernel.improvtools.application.presentation.view

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import com.brokenkernel.improvtools.application.data.model.ImprovToolsAppState
import com.brokenkernel.improvtools.application.data.model.NavigableRoute
import com.brokenkernel.improvtools.application.data.model.NavigableScreens
import com.brokenkernel.improvtools.ui.theme.ImprovToolsTheme


@Composable
internal fun OuterContentForMasterScreen(improvToolsState: ImprovToolsAppState) {
    val currentNavigableScreen: State<NavigableScreens> =
        improvToolsState.currentNavigableScreenAsState().collectAsState()

    ImprovToolsTheme {
        Surface {
            ImprovToolsNavigationDrawer(
                doNavigateToNavigableRoute = { route: NavigableRoute ->
                    improvToolsState.navigateTo(route)
                },
                currentNavigableScreen = currentNavigableScreen,
                drawerState = improvToolsState.drawerState,
                navController = improvToolsState.navController,
            )
        }
    }
}
