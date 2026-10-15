package com.brokenkernel.improvtools.application.presentation.view

import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.runtime.result.rememberResultEventBus
import androidx.navigation3.runtime.result.rememberResultEventBusNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.brokenkernel.improvtools.application.data.model.ImprovToolsAppState
import com.brokenkernel.improvtools.application.data.model.NavigableScreens
import com.brokenkernel.improvtools.application.data.model.rememberImprovToolsAppState
import com.brokenkernel.improvtools.application.navigation.applicationScreensEntryBuilder
import com.brokenkernel.improvtools.buzzer.impl.buzzerScreenEntryBuilder
import com.brokenkernel.improvtools.coreinfra.ImprovToolsNavigationKey
import com.brokenkernel.improvtools.coreinfra.ImprovToolsNavigator
import com.brokenkernel.improvtools.encyclopaedia.android.api.encyclopaediaScreensEntryBuilderPart2
import com.brokenkernel.improvtools.encyclopaedia.encyclopaediaScreensEntryBuilder
import com.brokenkernel.improvtools.suggestionGenerator.presentation.view.suggestionsScreenEntryBuilder
import com.brokenkernel.improvtools.timer.presentation.view.timerScreenEntryBuilder
import com.brokenkernel.improvtools.tonguetwister.impl.tonguetwisterScreenEntryBuilder
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
private fun NavigableScreenNavigationDrawerItem(
    improvToolsAppState: ImprovToolsAppState,
    goTo: (ImprovToolsNavigationKey) -> Unit,
    screen: NavigableScreens,
    closeNavMenuCallback: () -> Unit,
    modifier: Modifier = Modifier,
) {
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
            goTo(screen.matchingRoute)
        },
        selected = false,
        modifier = modifier,
    )
}

@Composable
internal fun ImprovToolsNavigationDrawer(
    initialScreen: NavigableScreens,
    modifier: Modifier = Modifier,
) {
    val improvToolsNavigator = ImprovToolsNavigator.Factory.create(initialScreen.matchingRoute)

    val scope: CoroutineScope = rememberCoroutineScope()

    val improvToolsAppState: ImprovToolsAppState = rememberImprovToolsAppState()
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

    val allDestinations = persistentListOf(
        NavigableScreens.SuggestionGeneratorScreen,
        NavigableScreens.TimerScreen,
        NavigableScreens.BuzzerScreen,
        NavigableScreens.GamesPageScreen,
        NavigableScreens.PeoplePageScreen,
        NavigableScreens.EmotionsPageScreen,
        NavigableScreens.GlossaryPageScreen,
        NavigableScreens.ThesaurusPageScreen,
        NavigableScreens.TipsAndAdviceScreen,
        NavigableScreens.TongueTwisterScreen,
        NavigableScreens.AboutScreen,
        NavigableScreens.PrivacyScreen,
        NavigableScreens.LibrariesScreen,
    )

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            allDestinations.forEach {
                item(
                    selected = it.matchingRoute == improvToolsNavigator.current(),
                    onClick = {
                        improvToolsNavigator.goTo(it.matchingRoute)
                    },
                    icon = {
                        Icon(
                            imageVector = it.icon(),
                            contentDescription = stringResource(it.contentDescription),
                        )
                    },
                    label = { Text(stringResource(it.titleResource)) },
                )
            }
        },
    ) {
        ImprovToolsScaffold(
            improvToolsAppState = improvToolsAppState,
            currentRoute = improvToolsNavigator.current(),
            navMenuButtonPressedCallback = {},
        ) {
            SharedTransitionLayout {
                val resultEventBus = rememberResultEventBus()
                NavDisplay(
                    backStack = improvToolsNavigator.backstack,
                    onBack = { improvToolsNavigator.goBack() },
                    entryDecorators =
                    listOf(
                        rememberSaveableStateHolderNavEntryDecorator(),
                        rememberViewModelStoreNavEntryDecorator(),
                        rememberResultEventBusNavEntryDecorator(resultEventBus = resultEventBus),
                    ),
                    entryProvider =
                    entryProvider {
                        suggestionsScreenEntryBuilder(
                            setBottomSheet = improvToolsAppState::setBottomSheetTo,
                            improvToolsNavigator = improvToolsNavigator, // TODO
                        )
                        encyclopaediaScreensEntryBuilder(
                            backstack = improvToolsNavigator.backstack, // TODO
                        )
                        encyclopaediaScreensEntryBuilderPart2(
                            improvToolsNavigator::goTo,
                        )
                        buzzerScreenEntryBuilder()
                        timerScreenEntryBuilder()
                        tonguetwisterScreenEntryBuilder()
                        applicationScreensEntryBuilder(
                            improvToolsNavigator::goTo,
                        )
                    },
                    sharedTransitionScope = this,
                )
            }
        }
    }
}

//    ModalNavigationDrawer(
//        drawerState = drawerState,
//        drawerContent = {
//            ModalDrawerSheet {
//                Column(
//                    modifier = Modifier
//                        .padding(horizontal = 16.dp)
//                        .verticalScroll(rememberScrollState()),
//                ) {
//                    Text(
//                        stringResource(R.string.app_name),
//                        modifier = Modifier.padding(16.dp),
//                        style = MaterialTheme.typography.titleLarge,
//                    )
//                    HorizontalDivider()
//                    Text(
//                        stringResource(R.string.navigation_useful_tools_category),
//                        modifier = Modifier.padding(16.dp),
//                        style = MaterialTheme.typography.titleMedium,
//                    )

//
//                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
//
//                    Text(
//                        stringResource(R.string.navigation_encyclopaedia_category),
//                        modifier = Modifier.padding(16.dp),
//                        style = MaterialTheme.typography.titleMedium,
//                    )
//                    NavigableScreenNavigationDrawerItem(
//                        improvToolsAppState,
//                        improvToolsNavigator::goTo,
//
//                        ::closeNavMenu,
//                    )
//                    NavigableScreenNavigationDrawerItem(
//                        improvToolsAppState,
//                        improvToolsNavigator::goTo,
//                        ::closeNavMenu,
//                    )
//                    NavigableScreenNavigationDrawerItem(
//                        improvToolsAppState,
//                        improvToolsNavigator::goTo,
//                        ::closeNavMenu,
//                    )
//                    NavigableScreenNavigationDrawerItem(
//                        improvToolsAppState,
//                        improvToolsNavigator::goTo,

//                        ::closeNavMenu,
//                    )
//                    NavigableScreenNavigationDrawerItem(
//                        improvToolsAppState,
//                        improvToolsNavigator::goTo,

//                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
//                    Text(
//                        stringResource(R.string.navigation_settings_category),
//                        modifier = Modifier.padding(16.dp),
//                        style = MaterialTheme.typography.titleMedium,
//                    )
//                    NavigableScreenNavigationDrawerItem(
//                        improvToolsAppState,
//                        improvToolsNavigator::goTo,
//                        NavigableScreens.SettingsScreen,
//                        ::closeNavMenu,
//                    )
//                    NavigableScreenNavigationDrawerItem(
//                        improvToolsAppState,
//                        improvToolsNavigator::goTo,
//
//                        ::closeNavMenu,
//                    )
//                    Spacer(Modifier.height(12.dp))
//                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
//                    Text(
//                        stringResource(R.string.navigation_legal_category),
//                        modifier = Modifier.padding(16.dp),
//                        style = MaterialTheme.typography.titleMedium,
//                    )
//                    NavigableScreenNavigationDrawerItem(
//                        improvToolsAppState,
//                        improvToolsNavigator::goTo,
//
//                        ::closeNavMenu,
//                    )
//                    NavigableScreenNavigationDrawerItem(
//                        improvToolsAppState,
//                        improvToolsNavigator::goTo,
//
//                        ::closeNavMenu,
//                    )
//                }
//            }
//        },
//        modifier = modifier,
//    ) {
//        ImprovToolsScaffold(
//            improvToolsAppState,
//            improvToolsNavigator.current(), // TODO
//            navMenuButtonPressedCallback = {
//                invertNavMenuState()
//            },
//        ) {

//    }
