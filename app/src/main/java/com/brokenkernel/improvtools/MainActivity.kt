package com.brokenkernel.improvtools

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ImprovToolsTheme {
        Greeting("Android")
    }
}



@Composable
fun Conversation(messages: List<SuggestionPair>) {
    LazyColumn {
        items(messages) { message ->
            SuggestionPairCard(message)
        }
    }
}

@Preview
@Composable
fun PreviewConversation() {
    MaterialTheme {
        Surface {
            Conversation(SampleData.conversationSample)
        }
    }
}