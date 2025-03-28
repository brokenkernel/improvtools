package com.brokenkernel.improvtools

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("hello") } ,
                )
            },
            bottomBar = {
                BottomAppBar() {
                    Text("Good Bye")
                }
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
    }
}
