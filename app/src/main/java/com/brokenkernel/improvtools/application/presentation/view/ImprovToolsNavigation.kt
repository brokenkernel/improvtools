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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.application.data.model.NavigableScreens
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
private fun NavigableScreenNavigationDrawerItem(
    screen: NavigableScreens,
    onNavMenuClickCallback: (NavigableScreens) -> Unit,
    currentNavigableScreen: NavigableScreens,
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
            onNavMenuClickCallback(screen)
        },
        selected = (screen.route == currentNavigableScreen.route),
    )
}

@Composable
internal fun ImprovToolsBottomBar(
    currentNavigableScreen: NavigableScreens,
    doNavigateToNavigableScreen: (NavigableScreens) -> Unit,
) {
    NavigationBar {
        NavigationBarItem(
            selected = (currentNavigableScreen.route == NavigableScreens.SuggestionGenerator.route),
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
                doNavigateToNavigableScreen(NavigableScreens.SuggestionGenerator)
            }
        )
        NavigationBarItem(
            selected = (currentNavigableScreen.route == NavigableScreens.Timer.route),
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
                doNavigateToNavigableScreen(NavigableScreens.Timer)
            }
        )
    }
}

@Composable
internal fun ImprovToolsNavigationDrawer(
    drawerState: DrawerState,
    doNavigateToNavigableScreen: (na: NavigableScreens) -> Unit,
    drawerNavController: NavHostController,
    currentNavigableScreen: NavigableScreens,
) {

    val scope: CoroutineScope = rememberCoroutineScope()

    fun closeNavMenu() {
        scope.launch {
            drawerState.apply {
                close()
            }
        }
    }


    fun invertNavMenuState(): Unit {
        scope.launch {
            drawerState.apply {
                if (isClosed) open() else close()
            }
        }
    }

    fun doNavigateToNavigableScreenWithNavClosure(na: NavigableScreens): Unit {
        doNavigateToNavigableScreen(na)
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
                        { it ->  doNavigateToNavigableScreenWithNavClosure(it) },
                        currentNavigableScreen,
                    )
                    NavigableScreenNavigationDrawerItem(
                        NavigableScreens.Timer,
                        { it ->  doNavigateToNavigableScreenWithNavClosure(it) },
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
                        { it ->  doNavigateToNavigableScreenWithNavClosure(it) },
                        currentNavigableScreen,
                    )
                    NavigableScreenNavigationDrawerItem(
                        NavigableScreens.HelpAndAbout,
                        { it ->  doNavigateToNavigableScreenWithNavClosure(it) },
                        currentNavigableScreen,
                    )
                    Spacer(Modifier.height(12.dp))
                }
            }
        },
        content = {
            ImprovToolsScaffold(
                content = {
                    // TODO: replace with event system instead of passing controller??
                    DrawerNavGraph(drawerNavController = drawerNavController)
                },
                currentNavigableScreen = currentNavigableScreen,
                doNavigateToNavigableScreen = { it ->  doNavigateToNavigableScreenWithNavClosure(it) },
                navMenuButtonPressedCallback = {
                    invertNavMenuState()
                },

                )
        }
    )
}