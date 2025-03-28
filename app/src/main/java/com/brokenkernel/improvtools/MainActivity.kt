package com.brokenkernel.improvtools

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.brokenkernel.improvtools.suggestionGenerator.presentation.view.ClickableTableCell
import com.brokenkernel.improvtools.suggestionGenerator.presentation.view.TableCell
import androidx.lifecycle.viewmodel.compose.viewModel
import com.brokenkernel.improvtools.suggestionGenerator.data.model.SuggestionCategory
import com.brokenkernel.improvtools.suggestionGenerator.presentation.viewmodel.SuggestionScreenViewModel
import com.brokenkernel.improvtools.ui.theme.ImprovToolsTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val suggestionsViewModel: SuggestionScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()




        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                suggestionsViewModel.initAllSuggestions()
            }
        }

        setContent {
            ImprovToolsTheme {
                Scaffold { innerPadding ->
                    SuggestionsScreen(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = suggestionsViewModel,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewSuggestionPairList() {
    val suggestionsViewModel = SuggestionScreenViewModel()
//    suggestionsViewModel.initAllSuggestions()

    MaterialTheme {
        Surface {
            SuggestionsScreen(
                modifier = Modifier.padding(16.dp),
                viewModel = suggestionsViewModel
            )
        }
    }
}

@Composable
fun SuggestionsScreen(
    modifier: Modifier,
    viewModel: SuggestionScreenViewModel = viewModel()
) {

    val gameUiState by viewModel.uiState.collectAsState()

    val categoryWeight = .3f
    val audienceIdeaWeight = .7f
    // assert total is 100.
    LazyColumn(
        modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        // Header
        item {
            Row(
                Modifier
                    .background(MaterialTheme.colorScheme.secondary)
                    .fillMaxWidth()
            ) {
                TableCell(
                    text = "Category",
                    weight = categoryWeight,
                    style = MaterialTheme.typography.titleLarge,
                )
                TableCell(
                    text = "Audience Idea",
                    weight = audienceIdeaWeight,
                    style = MaterialTheme.typography.titleLarge,
                )
            }
        }
        // Table
        items(SuggestionCategory.entries) { suggestionData ->
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TableCell(
                    text = suggestionData.title,
                    weight = categoryWeight,
                    style = MaterialTheme.typography.bodyMedium,
                )
                ClickableTableCell(
                    text = gameUiState.audienceSuggestions.getOrDefault(
                        suggestionData,
                        "unknown"
                    ), //  TODO I shouldn't have to do this. Maybe EnumMap
                    weight = audienceIdeaWeight,
                    style = MaterialTheme.typography.bodyMedium,
                    onClick = {
                        viewModel.updateSuggestionFor(suggestionData)
                    }
                )
            }
        }
    }
}