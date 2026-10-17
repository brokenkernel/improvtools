package com.brokenkernel.improvtools.application.presentation.view

import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
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
import com.brokenkernel.improvtools.coreinfra.BottomSheetSceneStrategy
import com.brokenkernel.improvtools.coreinfra.ImprovToolsNavigationKey
import com.brokenkernel.improvtools.coreinfra.ImprovToolsNavigator
import com.brokenkernel.improvtools.encyclopaedia.android.api.encyclopaediaScreensEntryBuilderPart2
import com.brokenkernel.improvtools.encyclopaedia.encyclopaediaScreensEntryBuilder
import com.brokenkernel.improvtools.suggestionGenerator.presentation.view.suggestionsScreenEntryBuilder
import com.brokenkernel.improvtools.suggestions.api.suggestionsScreenEntryBuilderPart2
import com.brokenkernel.improvtools.timer.presentation.view.timerScreenEntryBuilder
import com.brokenkernel.improvtools.tonguetwister.impl.tonguetwisterScreenEntryBuilder

@Composable
private fun ApplicationDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    goTo: (ImprovToolsNavigationKey) -> Unit,
    modifier: Modifier = Modifier,
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = {},
        modifier = modifier,
    ) {
        val allDestinations: List<NavigableScreens> =
            NavigableScreens
                .entries
                .filter { it.additionalMenuNavigationWeight != null }
                .sortedBy { it.additionalMenuNavigationWeight }

        Column {
            allDestinations.forEach {
                DropdownMenuItem(
                    text = { Text(stringResource(it.titleResource)) },
                    onClick = {
                        goTo(it.matchingRoute)
                        onDismissRequest()
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = it.icon(),
                            contentDescription = stringResource(it.contentDescription),
                        )
                    },
                )
            }
        }
    }
}

@Composable
internal fun ImprovToolsNavigationDrawer(
    initialScreen: NavigableScreens,
    modifier: Modifier = Modifier,
) {
    val improvToolsNavigator = ImprovToolsNavigator.Factory.create(initialScreen.matchingRoute)

    val improvToolsAppState: ImprovToolsAppState = rememberImprovToolsAppState()

    val allDestinations: List<NavigableScreens> =
        NavigableScreens
            .entries
            .filter { it.navigationBarNavigationWeight != null }
            .sortedBy { it.navigationBarNavigationWeight }

    var isDropdownMenuExpanded: Boolean by remember { mutableStateOf(false) }

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
            // TOOD: figure out if this is the right way to handle this.
            item(
                selected = false,
                onClick = {
                    isDropdownMenuExpanded = !isDropdownMenuExpanded
                },
                icon = {
                    Column {
                        ApplicationDropdownMenu(
                            isDropdownMenuExpanded,
                            onDismissRequest = { isDropdownMenuExpanded = false },
                            goTo = improvToolsNavigator::goTo,
                        )
                        Icon(
                            imageVector = ImageVector.vectorResource(
                                R.drawable.more_vert_24dp_1f1f1f_fill0_wght400_grad0_opsz24,
                            ),
                            contentDescription = stringResource(R.string.navigation_open_screen_specific_menu),
                        )
                    }
                },
            )
        },
        modifier = modifier,
    ) {
        ImprovToolsScaffold(
            improvToolsAppState = improvToolsAppState,
            currentRoute = improvToolsNavigator.current(),
        ) {
            SharedTransitionLayout {
                val resultEventBus = rememberResultEventBus()
                val bottomSheetStrategy =
                    remember { BottomSheetSceneStrategy<ImprovToolsNavigationKey>() }
                NavDisplay(
                    backStack = improvToolsNavigator.backstack,
                    onBack = { improvToolsNavigator.goBack() },
                    entryDecorators =
                    listOf(
                        rememberSaveableStateHolderNavEntryDecorator(),
                        rememberViewModelStoreNavEntryDecorator(),
                        rememberResultEventBusNavEntryDecorator(resultEventBus = resultEventBus),
                    ),
                    sceneStrategies = listOf(bottomSheetStrategy),
                    entryProvider =
                    entryProvider {
                        suggestionsScreenEntryBuilder(
                            setBottomSheet = improvToolsAppState::setBottomSheetTo,
                            improvToolsNavigator = improvToolsNavigator, // TODO
                        )
                        suggestionsScreenEntryBuilderPart2()
                        encyclopaediaScreensEntryBuilder(
                            improvToolsNavigator = improvToolsNavigator,
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
