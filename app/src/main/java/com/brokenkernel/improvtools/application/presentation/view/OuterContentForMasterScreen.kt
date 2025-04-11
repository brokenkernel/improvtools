package com.brokenkernel.improvtools.application.presentation.view

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.brokenkernel.improvtools.application.data.model.NavigableScreens
import com.brokenkernel.improvtools.application.presentation.viewmodel.OuterContentForMasterScreenViewModel
import com.brokenkernel.improvtools.infrastructure.ImprovToolsNavigator
import com.brokenkernel.improvtools.ui.theme.ImprovToolsTheme


@Composable
internal fun OuterContentForMasterScreen(viewModel: OuterContentForMasterScreenViewModel = hiltViewModel<OuterContentForMasterScreenViewModel>()) {
    val improvToolsNavigator: ImprovToolsNavigator = viewModel.improvToolsNavigator
    val currentNavigableScreen: State<NavigableScreens> = improvToolsNavigator.destination.collectAsState()
    LaunchedEffect(currentNavigableScreen) {
        if (improvToolsNavigator.destination.value != currentNavigableScreen) {
            improvToolsNavigator.navigateTo(currentNavigableScreen.value)
        }
    }

    ImprovToolsTheme {
        Surface {
            ImprovToolsNavigationDrawer(
                doNavigateToNavigableScreen = { clickedItem: NavigableScreens ->
                    improvToolsNavigator.navigateTo(clickedItem)
                },
                currentNavigableScreen = currentNavigableScreen
            )
        }
    }
}
