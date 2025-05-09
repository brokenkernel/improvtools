package com.brokenkernel.improvtools.application.presentation.view

import androidx.compose.runtime.Composable
import com.brokenkernel.improvtools.application.data.model.ImprovToolsAppState
import com.brokenkernel.improvtools.application.data.model.NavigableScreens
import com.ramcosta.composedestinations.navigation.DestinationDependenciesContainer
import com.ramcosta.composedestinations.navigation.require
import com.ramcosta.composedestinations.scope.DestinationScope
import com.ramcosta.composedestinations.wrapper.DestinationWrapper

object SetScaffoldStateWrapper : DestinationWrapper {
    @Composable
    override fun <T> DestinationScope<T>.Wrap(screenContent: @Composable (() -> Unit)) {
        val navigableScreen: NavigableScreens = NavigableScreens.byRoute(destination)
        val depContainer: DestinationDependenciesContainer = buildDependencies()
        val improvToolsAppState: ImprovToolsAppState = depContainer.require()

        LaunchWrapper(
            onLaunchCallback = {
                improvToolsAppState.currentTitle.value = navigableScreen.titleResource
            },
        ) {
            if (navigableScreen.setExtraMenu != null) {
                navigableScreen.setExtraMenu.invoke(improvToolsAppState)
            } else {
                improvToolsAppState.extraMenu.value = null
            }
            screenContent()
        }
    }
}
