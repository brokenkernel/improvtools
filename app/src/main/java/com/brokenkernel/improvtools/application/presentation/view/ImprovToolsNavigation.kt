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
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.application.data.model.ImprovToolsNavigationGraph
import com.brokenkernel.improvtools.application.data.model.NavigableRoute
import com.brokenkernel.improvtools.application.data.model.NavigableScreens
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
private fun NavigableScreenNavigationDrawerItem(
    screen: NavigableScreens,
    onNavMenuClickCallback: (NavigableRoute) -> Unit,
    currentNavigableRoute: NavDestination?,
) {
    NavigationDrawerItem(
        label = { Text(stringResource(screen.titleResource)) },
        icon = {
            Icon(
                screen.icon,
                contentDescription = stringResource(screen.contentDescription),
            )
        },
        onClick = {
            onNavMenuClickCallback(screen.route)
        },
        selected = (
            currentNavigableRoute?.hierarchy?.any { it ->
                it.hasRoute(screen.route::class)
            } == true
            ),
    )
}

@Composable
internal fun ImprovToolsNavigationDrawer(
    doNavigateToNavigableRoute: (NavigableRoute) -> Unit,
    currentBackStackEntryAsState: State<NavBackStackEntry?>,
    drawerState: DrawerState,
    navController: NavHostController,
    initialRoute: NavigableRoute,
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
                        .verticalScroll(rememberScrollState()),
                ) {
                    Text(
                        stringResource(R.string.app_name),
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleLarge,
                    )
                    HorizontalDivider()
                    Text(
                        stringResource(R.string.navigation_useful_tools_category),
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleMedium,
                    )
                    NavigableScreenNavigationDrawerItem(
                        NavigableScreens.SuggestionGeneratorScreen,
                        { it -> doNavigateToNavigableRouteWithNavClosure(it) },
                        currentBackStackEntryAsState.value?.destination,
                    )
                    NavigableScreenNavigationDrawerItem(
                        NavigableScreens.TimerScreen,
                        { it -> doNavigateToNavigableRouteWithNavClosure(it) },
                        currentBackStackEntryAsState.value?.destination,
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    Text(
                        stringResource(R.string.navigation_encyclopaedia_category),
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleMedium,
                    )
                    NavigableScreenNavigationDrawerItem(
                        NavigableScreens.GamesPageScreen,
                        { it -> doNavigateToNavigableRouteWithNavClosure(it) },
                        currentBackStackEntryAsState.value?.destination,
                    )
                    NavigableScreenNavigationDrawerItem(
                        NavigableScreens.PeoplePageScreen,
                        { it -> doNavigateToNavigableRouteWithNavClosure(it) },
                        currentBackStackEntryAsState.value?.destination,
                    )
                    NavigableScreenNavigationDrawerItem(
                        NavigableScreens.EmotionsPageScreen,
                        { it -> doNavigateToNavigableRouteWithNavClosure(it) },
                        currentBackStackEntryAsState.value?.destination,
                    )
                    NavigableScreenNavigationDrawerItem(
                        NavigableScreens.ThesaurusPageScreen,
                        { it -> doNavigateToNavigableRouteWithNavClosure(it) },
                        currentBackStackEntryAsState.value?.destination,
                    )
                    NavigableScreenNavigationDrawerItem(
                        NavigableScreens.TipsAndAdviceScreen,
                        { it -> doNavigateToNavigableRouteWithNavClosure(it) },
                        currentBackStackEntryAsState.value?.destination,
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                    Text(
                        stringResource(R.string.navigation_settings_category),
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleMedium,
                    )
                    NavigableScreenNavigationDrawerItem(
                        NavigableScreens.SettingsScreen,
                        { it -> doNavigateToNavigableRouteWithNavClosure(it) },
                        currentBackStackEntryAsState.value?.destination,
                    )
                    NavigableScreenNavigationDrawerItem(
                        NavigableScreens.HelpAndAboutScreen,
                        { it -> doNavigateToNavigableRouteWithNavClosure(it) },
                        currentBackStackEntryAsState.value?.destination,
                    )
                    Spacer(Modifier.height(12.dp))
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                    Text(
                        stringResource(R.string.navigation_legal_category),
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleMedium,
                    )
                    NavigableScreenNavigationDrawerItem(
                        NavigableScreens.PrivacyScreen,
                        { it -> doNavigateToNavigableRouteWithNavClosure(it) },
                        currentBackStackEntryAsState.value?.destination,
                    )
                    NavigableScreenNavigationDrawerItem(
                        NavigableScreens.LibrariesScreen,
                        { it -> doNavigateToNavigableRouteWithNavClosure(it) },
                        currentBackStackEntryAsState.value?.destination,
                    )
                }
            }
        },
    ) {
        ImprovToolsScaffold(
            currentBackStackEntry = currentBackStackEntryAsState,
            navMenuButtonPressedCallback = {
                invertNavMenuState()
            },
            initialRoute = initialRoute,
        ) {
            // TODO: replace with event system instead of passing controller??
            ImprovToolsNavigationGraph(
                navController = navController,
                onNavigateToRoute = doNavigateToNavigableRoute,
                initialRoute = initialRoute,
            )
        }
    }
}
