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
    onClickity: (NavigableScreens) -> Unit,
    scope: CoroutineScope,
    drawerState: DrawerState,
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
            onClickity(screen)
            scope.launch {
                drawerState.close()
            }
        },
        selected = (screen.route == currentNavigableScreen.route),
    )
}

@Composable
internal fun ImprovToolsBottomBar(
    screen: NavigableScreens, // TODO: figure out better way to handle this
    drawerNavController: NavHostController,
    ) {
    NavigationBar {
        NavigationBarItem(
            selected = (screen.route == NavigableScreens.SuggestionGenerator.route),
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
                // TODO: change title: ideally based on event
                drawerNavController.navigate(NavigableScreens.SuggestionGenerator.route)
            }
        )
        NavigationBarItem(
            selected = (screen.route == NavigableScreens.Timer.route),
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
                // TODO: change title: ideally based on event
                drawerNavController.navigate(NavigableScreens.Timer.route)
            }
        )
    }
}

@Composable
internal fun ImprovToolsNavigationDrawer(
    drawerState: DrawerState,
    onClickity: (na: NavigableScreens) -> Unit,
    drawerNavController: NavHostController,
    currentNavigableScreen: NavigableScreens,
) {

    val scope: CoroutineScope = rememberCoroutineScope()

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
                        onClickity,
                        scope,
                        drawerState,
                        currentNavigableScreen
                    )
                    NavigableScreenNavigationDrawerItem(
                        NavigableScreens.Timer,
                        onClickity,
                        scope,
                        drawerState,
                        currentNavigableScreen
                    )


                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    Text(
                        stringResource(R.string.navigation_settings_category),
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                    NavigableScreenNavigationDrawerItem(
                        NavigableScreens.Settings,
                        onClickity,
                        scope,
                        drawerState,
                        currentNavigableScreen
                    )
                    NavigableScreenNavigationDrawerItem(
                        NavigableScreens.HelpAndAbout,
                        onClickity,
                        scope,
                        drawerState,
                        currentNavigableScreen
                    )
                    Spacer(Modifier.height(12.dp))
                }
            }
        },
        content = {
            ImprovToolsScaffold(
                screenTitle = stringResource(currentNavigableScreen.titleResource),
                content = {
                    // TODO: replace with event system instead of passing controller??
                    DrawerNavGraph(drawerNavController = drawerNavController)
                },
                menuScope = scope,
                drawerState = drawerState,
                drawerNavController = drawerNavController,
                currentNavigableScreen = currentNavigableScreen,
            )
        }
    )
}