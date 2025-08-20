package com.brokenkernel.improvtools.application.navigation

import androidx.compose.runtime.Composable
import com.brokenkernel.improvtools.application.data.model.ImprovToolsAppState
import com.brokenkernel.improvtools.application.data.model.NavigableScreens
import com.brokenkernel.improvtools.coreinfra.LaunchWrapper
import com.ramcosta.composedestinations.navigation.DestinationDependenciesContainer
import com.ramcosta.composedestinations.navigation.require
import com.ramcosta.composedestinations.scope.DestinationScope
import com.ramcosta.composedestinations.wrapper.DestinationWrapper

internal object SetScaffoldStateWrapper : DestinationWrapper {
    @SuppressWarnings("ComposableLambdaParameterNaming")
    @Composable
    override fun <T> DestinationScope<T>.Wrap(screenContent: @Composable (() -> Unit)) {
        val navigableScreen: NavigableScreens = NavigableScreens.Companion.byRoute(destination)
        val depContainer: DestinationDependenciesContainer = buildDependencies()
        val improvToolsAppState: ImprovToolsAppState = depContainer.require()

        LaunchWrapper(
            onLaunchCallback = {
                improvToolsAppState.setScaffoldData(
                    navigableScreen.titleResource,
                    navigableScreen.extraMenu,
                )
            },
        ) {
            screenContent()
        }
    }
}
