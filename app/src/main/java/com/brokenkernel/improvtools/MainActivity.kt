package com.brokenkernel.improvtools

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.brokenkernel.improvtools.application.data.model.NavigableScreens
import com.brokenkernel.improvtools.application.presentation.view.ImprovToolsNavigationDrawer
import com.brokenkernel.improvtools.suggestionGenerator.presentation.viewmodel.SuggestionScreenViewModel
import com.brokenkernel.improvtools.ui.theme.ImprovToolsTheme

class MainActivity : ComponentActivity() {
    private val suggestionsViewModel: SuggestionScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            OuterContentForSuggestionsScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSuggestionPairList() {
    OuterContentForSuggestionsScreen()
}

@Composable
fun OuterContentForSuggestionsScreen() {
    val drawerNavController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    // TODO: use hilt DI
    ImprovToolsTheme {
        Surface {
            ImprovToolsNavigationDrawer(
                drawerState = drawerState,
                drawerNavController = drawerNavController,
                onClickity = { clickedItem: NavigableScreens ->
                    drawerNavController.navigate(clickedItem.route)
                },
            )
        }
    }
}