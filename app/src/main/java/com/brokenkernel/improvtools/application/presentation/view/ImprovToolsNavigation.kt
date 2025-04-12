package com.brokenkernel.improvtools.application.presentation.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.application.data.model.NavigableRoute
import com.brokenkernel.improvtools.application.data.model.NavigableScreens
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
private fun NavigableScreenNavigationDrawerItem(
    screen: NavigableScreens,
    onNavMenuClickCallback: (NavigableRoute) -> Unit,
    currentNavigableScreen: State<NavigableScreens>,
) {
    NavigationDrawerItem(
        label = { Text(stringResource(screen.titleResource)) },
        icon = {
            Icon(
                screen.icon,
                contentDescription = stringResource(screen.contentDescription)
            )
        },
        onClick = {
            onNavMenuClickCallback(screen.route)
        },
        selected = (screen.route == currentNavigableScreen.value.route),
    )
}

@Suppress("unused") // reminds me if I want to get rid of the doubled scaffold.
@Composable
internal fun ImprovToolsBottomBar(
    currentNavigableScreen: State<NavigableScreens>,
    doNavigateToNavigableRoute: (NavigableRoute) -> Unit,
) {
    NavigationBar {
        NavigationBarItem(
            selected = (currentNavigableScreen.value.route == NavigableScreens.SuggestionGenerator.route),
            label = {
                NavigableScreens.SuggestionGenerator.titleResource
            },
            icon = {
                Icon(
                    NavigableScreens.SuggestionGenerator.icon,
                    contentDescription = stringResource(NavigableScreens.SuggestionGenerator.contentDescription)
                )
            },
            onClick = {
                doNavigateToNavigableRoute(NavigableRoute.SuggestionGeneratorRoute)
            }
        )
        NavigationBarItem(
            selected = (currentNavigableScreen.value.route == NavigableScreens.Timer.route),
            label = {
                NavigableScreens.Timer.titleResource
            },
            icon = {
                Icon(
                    NavigableScreens.Timer.icon,
                    contentDescription = stringResource(NavigableScreens.Timer.contentDescription)
                )

            },
            onClick = {
                doNavigateToNavigableRoute(NavigableRoute.TimerRoute)
            }
        )
    }
}

@Composable
internal fun ImprovToolsNavigationDrawer(
    doNavigateToNavigableRoute: (NavigableRoute) -> Unit,
    currentNavigableScreen: State<NavigableScreens>,
    drawerState: DrawerState,
    navController: NavHostController,
) {

    val scope: CoroutineScope = rememberCoroutineScope()

    fun closeNavMenu() {
        scope.launch {
            drawerState.apply {
                close()
            }
        }
    }

    fun invertNavMenuState() {
        scope.launch {
            drawerState.apply {
                if (isClosed) open() else close()
            }
        }
    }

    fun doNavigateToNavigableRouteWithNavClosure(nr: NavigableRoute) {
        doNavigateToNavigableRoute(nr)
        closeNavMenu()
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        stringResource(R.string.app_name),
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleLarge
                    )
                    HorizontalDivider()
                    Text(
                        stringResource(R.string.navigation_useful_tools_category),
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                    NavigableScreenNavigationDrawerItem(
                        NavigableScreens.SuggestionGenerator,
                        { it -> doNavigateToNavigableRouteWithNavClosure(it) },
                        currentNavigableScreen,
                    )
                    NavigableScreenNavigationDrawerItem(
                        NavigableScreens.Timer,
                        { it -> doNavigateToNavigableRouteWithNavClosure(it) },
                        currentNavigableScreen,
                    )
                    NavigableScreenNavigationDrawerItem(
                        NavigableScreens.WorkshopGenerator,
                        { it -> doNavigateToNavigableRouteWithNavClosure(it) },
                        currentNavigableScreen,
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    Text(
                        stringResource(R.string.navigation_encyclopaedia_category),
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                    NavigableScreenNavigationDrawerItem(
                        NavigableScreens.Encyclopaedia,
                        { it -> doNavigateToNavigableRouteWithNavClosure(it) },
                        currentNavigableScreen,
                    )
                    NavigableScreenNavigationDrawerItem(
                        NavigableScreens.TipsAndAdvice,
                        { it -> doNavigateToNavigableRouteWithNavClosure(it) },
                        currentNavigableScreen,
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                    Text(
                        stringResource(R.string.navigation_settings_category),
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                    NavigableScreenNavigationDrawerItem(
                        NavigableScreens.Settings,
                        { it -> doNavigateToNavigableRouteWithNavClosure(it) },
                        currentNavigableScreen,
                    )
                    NavigableScreenNavigationDrawerItem(
                        NavigableScreens.HelpAndAbout,
                        { it -> doNavigateToNavigableRouteWithNavClosure(it) },
                        currentNavigableScreen,
                    )
                    Spacer(Modifier.height(12.dp))
                }
            }
        },
    ) {
        ImprovToolsScaffold(
            currentNavigableScreen = currentNavigableScreen,
            doNavigateToNavigableRoute = { route -> doNavigateToNavigableRouteWithNavClosure(route) },
            navMenuButtonPressedCallback = {
                invertNavMenuState()
            },
            content = {
                // TODO: replace with event system instead of passing controller??
                DrawerNavGraph(
                    navController = navController,
                    currentNavigableScreen = currentNavigableScreen,
                    onNavigateToRoute = doNavigateToNavigableRoute,
                )
            }
        )
    }
}
