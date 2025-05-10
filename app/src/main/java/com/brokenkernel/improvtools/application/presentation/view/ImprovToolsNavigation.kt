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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.application.data.model.ImprovToolsAppState
import com.brokenkernel.improvtools.application.data.model.NavigableScreens
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.generated.navgraphs.ImprovToolsNavigationNavGraph
import com.ramcosta.composedestinations.navigation.dependency
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
private fun NavigableScreenNavigationDrawerItem(
    improvToolsAppState: ImprovToolsAppState,
    screen: NavigableScreens,
    closeNavMenuCallback: () -> Unit,
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
            closeNavMenuCallback()
            improvToolsAppState.navigator.navigate(screen.matchingRoute) {
                launchSingleTop = true
                restoreState = true
            }
        },
        selected = improvToolsAppState.amIOnScreen(screen),
    )
}

@Composable
internal fun ImprovToolsNavigationDrawer(
    improvToolsAppState: ImprovToolsAppState,
    drawerState: DrawerState,
    initialRoute: DirectionDestinationSpec,
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
                        improvToolsAppState,
                        NavigableScreens.SuggestionGeneratorScreen,
                        ::closeNavMenu,
                    )
                    NavigableScreenNavigationDrawerItem(
                        improvToolsAppState,
                        NavigableScreens.TimerScreen,
                        ::closeNavMenu,
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    Text(
                        stringResource(R.string.navigation_encyclopaedia_category),
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleMedium,
                    )
                    NavigableScreenNavigationDrawerItem(
                        improvToolsAppState,
                        NavigableScreens.GamesPageScreen,
                        ::closeNavMenu,
                    )
                    NavigableScreenNavigationDrawerItem(
                        improvToolsAppState,
                        NavigableScreens.PeoplePageScreen,
                        ::closeNavMenu,
                    )
                    NavigableScreenNavigationDrawerItem(
                        improvToolsAppState,
                        NavigableScreens.EmotionsPageScreen,
                        ::closeNavMenu,
                    )
                    NavigableScreenNavigationDrawerItem(
                        improvToolsAppState,
                        NavigableScreens.GlossaryPageScreen,
                        ::closeNavMenu,
                    )
                    NavigableScreenNavigationDrawerItem(
                        improvToolsAppState,
                        NavigableScreens.ThesaurusPageScreen,
                        ::closeNavMenu,
                    )
                    NavigableScreenNavigationDrawerItem(
                        improvToolsAppState,
                        NavigableScreens.TipsAndAdviceScreen,
                        ::closeNavMenu,
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                    Text(
                        stringResource(R.string.navigation_settings_category),
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleMedium,
                    )
                    NavigableScreenNavigationDrawerItem(
                        improvToolsAppState,
                        NavigableScreens.SettingsScreen,
                        ::closeNavMenu,
                    )
                    NavigableScreenNavigationDrawerItem(
                        improvToolsAppState,
                        NavigableScreens.AboutScreen,
                        ::closeNavMenu,
                    )
                    Spacer(Modifier.height(12.dp))
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                    Text(
                        stringResource(R.string.navigation_legal_category),
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleMedium,
                    )
                    NavigableScreenNavigationDrawerItem(
                        improvToolsAppState,
                        NavigableScreens.PrivacyScreen,
                        ::closeNavMenu,
                    )
                    NavigableScreenNavigationDrawerItem(
                        improvToolsAppState,
                        NavigableScreens.LibrariesScreen,
                        ::closeNavMenu,
                    )
                }
            }
        },
    ) {
        ImprovToolsScaffold(
            improvToolsAppState,
            navMenuButtonPressedCallback = {
                invertNavMenuState()
            },
        ) {
            DestinationsNavHost(
                navGraph = ImprovToolsNavigationNavGraph,
                navController = improvToolsAppState.navController,
                dependenciesContainerBuilder = {
                    dependency(improvToolsAppState)
                },
                start = initialRoute,
            )
        }
    }
}
