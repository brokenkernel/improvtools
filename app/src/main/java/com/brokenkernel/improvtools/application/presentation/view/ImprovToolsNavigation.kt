package com.brokenkernel.improvtools.application.presentation.view

import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.runtime.result.rememberResultEventBus
import androidx.navigation3.runtime.result.rememberResultEventBusNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.brokenkernel.improvtools.application.data.model.NavigableScreens
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
internal fun ImprovToolsNavigationDrawer(
    initialScreen: NavigableScreens,
    modifier: Modifier = Modifier,
) {
    val improvToolsNavigator = ImprovToolsNavigator.Factory.create(initialScreen.matchingRoute)

    val allDestinations: List<NavigableScreens> =
        NavigableScreens
            .entries
            .filter { it.navigationBarNavigationWeight != null }
            .sortedBy { it.navigationBarNavigationWeight }

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
        modifier = modifier,
    ) {
        ImprovToolsScaffold(
            improvToolsNavigator = improvToolsNavigator,
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
