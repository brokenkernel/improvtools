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
import androidx.navigation3.runtime.result.rememberResultEventBus
import androidx.navigation3.runtime.result.rememberResultEventBusNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.brokenkernel.improvtools.R
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
                        improvToolsNavigator::goTo,
                        NavigableScreens.SuggestionGeneratorScreen,
                        ::closeNavMenu,
                    )
                    NavigableScreenNavigationDrawerItem(
                        improvToolsAppState,
                        improvToolsNavigator::goTo,
                        NavigableScreens.TimerScreen,
                        ::closeNavMenu,
                    )
                    NavigableScreenNavigationDrawerItem(
                        improvToolsAppState,
                        improvToolsNavigator::goTo,
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
                        improvToolsNavigator::goTo,
                        NavigableScreens.GamesPageScreen,
                        ::closeNavMenu,
                    )
                    NavigableScreenNavigationDrawerItem(
                        improvToolsAppState,
                        improvToolsNavigator::goTo,
                        NavigableScreens.PeoplePageScreen,
                        ::closeNavMenu,
                    )
                    NavigableScreenNavigationDrawerItem(
                        improvToolsAppState,
                        improvToolsNavigator::goTo,
                        NavigableScreens.EmotionsPageScreen,
                        ::closeNavMenu,
                    )
                    NavigableScreenNavigationDrawerItem(
                        improvToolsAppState,
                        improvToolsNavigator::goTo,
                        NavigableScreens.GlossaryPageScreen,
                        ::closeNavMenu,
                    )
                    NavigableScreenNavigationDrawerItem(
                        improvToolsAppState,
                        improvToolsNavigator::goTo,
                        NavigableScreens.ThesaurusPageScreen,
                        ::closeNavMenu,
                    )
                    NavigableScreenNavigationDrawerItem(
                        improvToolsAppState,
                        improvToolsNavigator::goTo,
                        NavigableScreens.TipsAndAdviceScreen,
                        ::closeNavMenu,
                    )
                    NavigableScreenNavigationDrawerItem(
                        improvToolsAppState,
                        improvToolsNavigator::goTo,
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
                        improvToolsNavigator::goTo,
                        NavigableScreens.SettingsScreen,
                        ::closeNavMenu,
                    )
                    NavigableScreenNavigationDrawerItem(
                        improvToolsAppState,
                        improvToolsNavigator::goTo,
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
                        improvToolsNavigator::goTo,
                        NavigableScreens.PrivacyScreen,
                        ::closeNavMenu,
                    )
                    NavigableScreenNavigationDrawerItem(
                        improvToolsAppState,
                        improvToolsNavigator::goTo,
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
            improvToolsNavigator.current(), // TODO
            navMenuButtonPressedCallback = {
                invertNavMenuState()
            },
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
