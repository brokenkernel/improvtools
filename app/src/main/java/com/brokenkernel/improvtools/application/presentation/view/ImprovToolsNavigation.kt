package com.brokenkernel.improvtools.application.presentation.view

import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.application.data.model.ImprovToolsAppState
import com.brokenkernel.improvtools.application.data.model.NavigableScreens
import com.brokenkernel.improvtools.application.data.model.rememberImprovToolsAppState
import com.brokenkernel.improvtools.application.navigation.applicationScreensEntryBuilder
import com.brokenkernel.improvtools.buzzer.impl.buzzerScreenEntryBuilder
import com.brokenkernel.improvtools.coreinfra.ImprovToolsNavigationKey
import com.brokenkernel.improvtools.coreinfra.rememberParcelableBackStack
import com.brokenkernel.improvtools.encyclopaedia.encyclopaediaScreensEntryBuilder
import com.brokenkernel.improvtools.suggestionGenerator.presentation.view.suggestionsScreenEntryBuilder
import com.brokenkernel.improvtools.suggestions.api.SuggestionsScreenNavigationKey
import com.brokenkernel.improvtools.timer.impl.timerScreenEntryBuilder
import com.brokenkernel.improvtools.tonguetwister.impl.tonguetwisterScreenEntryBuilder
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.generated.app.navgraphs.ImprovToolsNavigationNavGraph
import com.ramcosta.composedestinations.navigation.dependency
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
private fun NavigableScreenNavigationDrawerItem(
    improvToolsAppState: ImprovToolsAppState,
    screen: NavigableScreens,
    closeNavMenuCallback: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val currentDestinationAsState = improvToolsAppState.currentDestinationAsState()

    NavigationDrawerItem(
        label = { Text(stringResource(screen.titleResource)) },
        icon = {
            Icon(
                screen.icon(),
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
        selected = currentDestinationAsState.value == screen.matchingRoute,
        modifier = modifier,
    )
}

@Composable
internal fun ImprovToolsNavigationDrawer(
    initialScreen: NavigableScreens,
    modifier: Modifier = Modifier,
) {
    val scope: CoroutineScope = rememberCoroutineScope()

    val improvToolsAppState: ImprovToolsAppState = rememberImprovToolsAppState(
        initialTitle = initialScreen.titleResource,
    )
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

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
                    NavigableScreenNavigationDrawerItem(
                        improvToolsAppState,
                        NavigableScreens.BuzzerScreen,
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
                    NavigableScreenNavigationDrawerItem(
                        improvToolsAppState,
                        NavigableScreens.TongueTwisterScreen,
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
        modifier = modifier,
    ) {
        ImprovToolsScaffold(
            improvToolsAppState,
            navMenuButtonPressedCallback = {
                invertNavMenuState()
            },
        ) {
            val backStack =
                rememberParcelableBackStack<ImprovToolsNavigationKey>(
                    SuggestionsScreenNavigationKey,
                )
            // eventually need to remove column; using this so I can have two 'scaffolds'
            Column {
                if (false) {
                    SharedTransitionLayout {
                        NavDisplay(
                            backStack = backStack,
                            onBack = { backStack.removeLastOrNull() },
                            entryDecorators =
                            listOf(
                                rememberSaveableStateHolderNavEntryDecorator(),
                                rememberViewModelStoreNavEntryDecorator(),
                            ),
                            entryProvider =
                            entryProvider {
                                suggestionsScreenEntryBuilder(
                                    navigator = improvToolsAppState.navigator,
                                    improvToolsAppState = improvToolsAppState,
                                )
                                encyclopaediaScreensEntryBuilder(
                                    navigator = improvToolsAppState.navigator,
                                    improvToolsAppState = improvToolsAppState,
                                )
                                buzzerScreenEntryBuilder()
                                timerScreenEntryBuilder()
                                tonguetwisterScreenEntryBuilder(
                                    navigator = improvToolsAppState.navigator,
                                )
                                applicationScreensEntryBuilder(
                                    navigator = improvToolsAppState.navigator,
                                )
                            },
                            sharedTransitionScope = this,
                        )
                    }
                }

                DestinationsNavHost(
                    navGraph = ImprovToolsNavigationNavGraph,
                    navController = improvToolsAppState.navController,
                    dependenciesContainerBuilder = {
                        // TODO: replace with per-module navigation functions
                        // https://composedestinations.rafaelcosta.xyz/v2/multi-module-setup#receive-navhost-parameters
                        // TODO: pull encyclopedia out to different module
                        dependency(improvToolsAppState)
                    },
                    start = initialScreen.matchingRoute,
                )
            }
        }
    }
}
