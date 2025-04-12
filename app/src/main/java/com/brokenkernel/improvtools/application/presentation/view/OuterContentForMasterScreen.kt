package com.brokenkernel.improvtools.application.presentation.view

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import com.brokenkernel.improvtools.application.data.model.ImprovToolsAppState
import com.brokenkernel.improvtools.application.data.model.NavigableRoute
import com.brokenkernel.improvtools.ui.theme.ImprovToolsTheme


@Composable
internal fun OuterContentForMasterScreen(improvToolsState: ImprovToolsAppState) {
    val currentNavigableRoute: State<NavigableRoute> =
        improvToolsState.currentNavigableRouteAsState().collectAsState()

    ImprovToolsTheme {
        Surface {
            ImprovToolsNavigationDrawer(
                doNavigateToNavigableRoute = { route: NavigableRoute ->
                    improvToolsState.navigateTo(route)
                },
                currentNavigableRoute = currentNavigableRoute,
                drawerState = improvToolsState.drawerState,
                navController = improvToolsState.navController,
            )
        }
    }
}
