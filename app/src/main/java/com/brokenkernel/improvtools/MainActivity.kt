package com.brokenkernel.improvtools

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.viewModelFactory
import com.brokenkernel.improvtools.suggestionGenerator.data.model.SuggestionDatum
import com.brokenkernel.improvtools.suggestionGenerator.presentation.viewmodel.SuggestionsActivityViewModel
import com.brokenkernel.improvtools.ui.theme.ImprovToolsTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val viewModel: SuggestionsActivityViewModel by viewModels()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.initAllSuggestions()
            }
        }

        setContent {
            ImprovToolsTheme {
                Scaffold { innerPadding ->
                    SuggestionsScreen(
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewSuggestionPairList() {
    MaterialTheme {
        Surface {
            SuggestionsScreen(
                modifier = Modifier.padding(16.dp),
            )
        }
    }
}

@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float,
    style: TextStyle,
) {
    Text(
        text = text,
        modifier = Modifier
            .border(1.dp, MaterialTheme.colorScheme.tertiary)
            .weight(weight)
            .padding(8.dp),
        style = style,
    )
}

@Composable
fun RowScope.ClickableTableCell(
    text: String,
    weight: Float,
    style: TextStyle,
) {
    TextButton(
        onClick = {},
        modifier = Modifier
            .border(1.dp, MaterialTheme.colorScheme.tertiary)
            .weight(weight)
    )
    {
        Text(
            text = text,
            style = style,
        )
    }
}


@Composable
fun SuggestionsScreen(modifier: Modifier) {
    val categoryWeight = .3f
    val audienceIdeaWeight = .7f
    // assert total is 100.
    LazyColumn(modifier) {
        // Header
        item {
            Row(Modifier
                .background(MaterialTheme.colorScheme.secondary)
                .fillMaxWidth()) {
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
        items(SuggestionDatum.allCategories) { suggestionData ->
            Row(Modifier.fillMaxWidth()) {
                TableCell(
                    text = suggestionData.title,
                    weight = categoryWeight,
                    style = MaterialTheme.typography.bodyMedium,
                )
                ClickableTableCell(
                    text = suggestionData.ideas.random(),
                    weight = audienceIdeaWeight,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}