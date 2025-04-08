package com.brokenkernel.improvtools.application.data.model

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.brokenkernel.improvtools.application.presentation.view.ImprovToolsNavigationDrawer
import com.brokenkernel.improvtools.application.presentation.viewmodel.OuterContentForMasterScreenViewModel
import com.brokenkernel.improvtools.infrastructure.ImprovToolsNavigator
import com.brokenkernel.improvtools.ui.theme.ImprovToolsTheme


@Composable
internal fun OuterContentForMasterScreen(viewModel: OuterContentForMasterScreenViewModel = hiltViewModel<OuterContentForMasterScreenViewModel>()) {
    val drawerNavController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val improvToolsNavigator: ImprovToolsNavigator = viewModel.improvToolsNavigator
    val currentNavigableScreen: State<NavigableScreens> = improvToolsNavigator.destination.collectAsState()
    LaunchedEffect(currentNavigableScreen) {
        if (improvToolsNavigator.destination.value != currentNavigableScreen) {
            improvToolsNavigator.navigateTo(currentNavigableScreen.value)
        }
    }

//
//
//    drawerNavController.addOnDestinationChangedListener {
//            controller: NavController,
//            destination: NavDestination,
//            args: Bundle?,
//        ->
//        val whichScreen: NavigableScreens? = NavigableScreens.byRoute(destination.route)
//        if (whichScreen != null) {
//            currentNavigableScreen = whichScreen
//        }
//    }

    ImprovToolsTheme {
        Surface {
            ImprovToolsNavigationDrawer(
                drawerState = drawerState,
                drawerNavController = drawerNavController,
                doNavigateToNavigableScreen = { clickedItem: NavigableScreens ->
                    improvToolsNavigator.navigateTo(clickedItem)
                },
                currentNavigableScreen = currentNavigableScreen
            )
        }
    }
}
