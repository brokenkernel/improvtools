package com.brokenkernel.improvtools

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.brokenkernel.improvtools.application.presentation.view.ImprovToolsNavigationDrawer
import com.brokenkernel.improvtools.suggestionGenerator.presentation.view.SuggestionsScreen
import com.brokenkernel.improvtools.suggestionGenerator.presentation.viewmodel.SuggestionScreenViewModel
import com.brokenkernel.improvtools.ui.theme.ImprovToolsTheme

class MainActivity : ComponentActivity() {
    private val suggestionsViewModel: SuggestionScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            OuterContentForSuggestionsScreen(suggestionsViewModel)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSuggestionPairList() {
    val suggestionsViewModel = SuggestionScreenViewModel()
    OuterContentForSuggestionsScreen(viewModel = suggestionsViewModel)
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OuterContentForSuggestionsScreen(
    viewModel: SuggestionScreenViewModel
) {


    ImprovToolsTheme {
        ImprovToolsNavigationDrawer({
            Scaffold(
                topBar = {
                    CenterAlignedTopAppBar(
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            titleContentColor = MaterialTheme.colorScheme.primary,
                        ),
                        title = { Text(stringResource(R.string.suggestions_activity_title)) } ,
                        scrollBehavior = TopAppBarDefaults
                            .exitUntilCollapsedScrollBehavior(
                                rememberTopAppBarState()),
                    )
                },
                bottomBar = {
                }
            ) { innerPadding ->
                Surface(modifier = Modifier.fillMaxSize()
                        .padding(innerPadding)
                ) {
                    SuggestionsScreen(
                        viewModel = viewModel
                    )
                }
            }
        })
    }
}