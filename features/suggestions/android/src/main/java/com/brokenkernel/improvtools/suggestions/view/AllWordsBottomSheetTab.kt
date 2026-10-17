package com.brokenkernel.improvtools.suggestions.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.brokenkernel.improvtools.suggestions.viewmodel.SuggestionsScreenAllWordsViewModel

@Composable
internal fun AllWordsBottomSheetTab(
    category: String,
    modifier: Modifier = Modifier,
    viewModel: SuggestionsScreenAllWordsViewModel = hiltViewModel(),
) {
    Column(modifier) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            Text(category, style = MaterialTheme.typography.titleMedium)
        }
        LazyColumn {
            val words = viewModel.getIdeaForCategory(category)
            items(words, key = { it }) { word ->
                ListItem(
                    headlineContent = {
                        Text(word) // TODO: maybe add explanation
                    },
                )
            }
        }
    }
}
