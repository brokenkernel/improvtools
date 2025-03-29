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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.brokenkernel.improvtools.application.data.model.NavigableScreens
import com.brokenkernel.improvtools.application.presentation.view.ImprovToolsNavigationDrawer
import com.brokenkernel.improvtools.ui.theme.ImprovToolsTheme

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

    // the next two are weird and should be resolvable by tracking 'current screen metadta' instead of individual state,
    // also annoying since the default is multiple-times replicated, but good enough for now.
    var currentScreenTitleResource by rememberSaveable { mutableIntStateOf(NavigableScreens.SuggestionGenerator.titleResource) }
    var currentRoute by rememberSaveable { mutableStateOf(NavigableScreens.SuggestionGenerator.route) }

    // TODO: use hilt DI
    ImprovToolsTheme {
        Surface {
            ImprovToolsNavigationDrawer(
                drawerState = drawerState,
                drawerNavController = drawerNavController,
                onClickity = { clickedItem: NavigableScreens ->
                    drawerNavController.navigate(clickedItem.route)
                    currentScreenTitleResource = clickedItem.titleResource
                    currentRoute = clickedItem.route
                },
                currentScreenTitleResource = currentScreenTitleResource,
                currentRoute = currentRoute,
            )
        }
    }
}