package com.brokenkernel.improvtools

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.compose.rememberNavController
import com.brokenkernel.improvtools.application.data.model.NavigableScreens
import com.brokenkernel.improvtools.application.presentation.view.ImprovToolsNavigationDrawer
import com.brokenkernel.improvtools.ui.theme.ImprovToolsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            OuterContentForMasterScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSuggestionPairList() {
    OuterContentForMasterScreen()
}

@Composable
fun OuterContentForMasterScreen() {
    val drawerNavController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    var currentNavigableScreen by rememberSaveable { mutableStateOf(NavigableScreens.SuggestionGenerator) }

    drawerNavController.addOnDestinationChangedListener {
            controller: NavController,
            destination: NavDestination,
            args: Bundle?,
        ->
        val whichScreen = NavigableScreens.byRoute(destination.route)
        if (whichScreen != null) {
            currentNavigableScreen = whichScreen
        }
    }

    ImprovToolsTheme {
        Surface {
            ImprovToolsNavigationDrawer(
                drawerState = drawerState,
                drawerNavController = drawerNavController,
                doNavigateToNavigableScreen = { clickedItem: NavigableScreens ->
                    drawerNavController.navigate(clickedItem.route)
                },
                currentNavigableScreen = currentNavigableScreen
            )
        }
    }
}
