package com.brokenkernel.improvtools.application.presentation.view

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import com.brokenkernel.improvtools.application.data.model.NavigableScreens
import com.brokenkernel.improvtools.application.data.model.rememberImprovToolsAppState
import com.brokenkernel.improvtools.ui.theme.ImprovToolsTheme


@Composable
internal fun OuterContentForMasterScreen() {
    val improvToolsState = rememberImprovToolsAppState()
    val currentNavigableScreen: State<NavigableScreens> = improvToolsState.currentNavigableScreen.collectAsState()

    ImprovToolsTheme {
        Surface {
            ImprovToolsNavigationDrawer(
                doNavigateToNavigableScreen = { clickedItem: NavigableScreens ->
                    improvToolsState.navigateTo(clickedItem)
                },
                currentNavigableScreen = currentNavigableScreen
            )
        }
    }
}
