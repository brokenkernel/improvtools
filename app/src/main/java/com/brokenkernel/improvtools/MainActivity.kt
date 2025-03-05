package com.brokenkernel.improvtools

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.brokenkernel.improvtools.ui.theme.ImprovToolsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ImprovToolsTheme {
                Scaffold { innerPadding ->
                    SuggestionsScreen(
                        SuggestionPairDatum.suggestionPairSamples,
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}

data class SuggestionPair(val category: String, val audienceIdea: String)

@Composable
fun SuggestionPairCard(msg: SuggestionPair) {
    Column {
        Text(text = msg.category, fontStyle = FontStyle.Italic)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = msg.audienceIdea)
    }
}

@Composable
fun SuggestionPairList(messages: List<SuggestionPair>) {
    LazyColumn {
        item {
            Text(
                text = "Category",
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                style = MaterialTheme.typography.headlineLarge
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Suggestion",
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                style = MaterialTheme.typography.headlineLarge
            )

        }

        items(messages) { message ->
            SuggestionPairCard(message)
        }
    }
}

@Preview
@Composable
fun PreviewSuggestionPairList() {
    MaterialTheme {
        Surface {
            SuggestionPairList(SuggestionPairDatum.suggestionPairSamples)
        }
    }
}

@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float
) {
    Text(
        text = text,
        Modifier
            .border(1.dp, Color.Black)
            .weight(weight)
            .padding(8.dp)
    )
}



@Composable
fun SuggestionsScreen(suggestionPairs: List<SuggestionPair>, modifier: Modifier) {
    val categoryWeight = .3f
    val audienceIdeaWeight = .7f
    // assert total is 100.
    LazyColumn(modifier) {
        // Header
        item {
            Row(Modifier.background(Color.Gray)) {
                TableCell(text = "Category", weight = categoryWeight)
                TableCell(text = "Audience Idea", weight = audienceIdeaWeight)
            }
        }
        // Table
        items(suggestionPairs) { suggestionPair ->
            Row(Modifier.fillMaxWidth()) {
                TableCell(text = suggestionPair.category, weight = categoryWeight)
                TableCell(text = suggestionPair.audienceIdea, weight = audienceIdeaWeight)
            }
        }
    }
}