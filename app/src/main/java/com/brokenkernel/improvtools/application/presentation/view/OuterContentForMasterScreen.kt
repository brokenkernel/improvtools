package com.brokenkernel.improvtools.application.presentation.view

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.navigation.NavBackStackEntry
import com.brokenkernel.improvtools.application.data.model.ImprovToolsAppState
import com.brokenkernel.improvtools.application.data.model.NavigableRoute
import com.brokenkernel.improvtools.ui.theme.ImprovToolsTheme

@Composable
internal fun OuterContentForMasterScreen(improvToolsState: ImprovToolsAppState, initialRoute: NavigableRoute) {
    val currentBackStackEntryAsState: State<NavBackStackEntry?> = improvToolsState
        .currentBackStackEntryAsState()

    ImprovToolsTheme {
        Surface {
            ImprovToolsNavigationDrawer(
                doNavigateToNavigableRoute = { route: NavigableRoute ->
                    improvToolsState.navigateTo(route)
                },
                currentBackStackEntryAsState = currentBackStackEntryAsState,
                drawerState = improvToolsState.drawerState,
                navController = improvToolsState.navController,
                initialRoute = initialRoute,
            )
        }
    }
}
