package com.brokenkernel.improvtools

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
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

@Composable
fun OuterContentForSuggestionsScreen(
    viewModel: SuggestionScreenViewModel
) {
    ImprovToolsTheme {
        // TODO: what is Scaffold. do I need it?
        Surface(modifier = Modifier.fillMaxSize()) {
            SuggestionsScreen(
                viewModel = viewModel
            )
        }
    }
}
