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
import kotlinx.collections.immutable.ImmutableList

// TODO: should be internal
@Composable
public fun AllWordsBottomSheetTab(
    header: String,
    words: ImmutableList<String>,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            Text(header, style = MaterialTheme.typography.titleMedium)
        }
        LazyColumn {
            items(words) { word ->
                ListItem(
                    headlineContent = {
                        Text(word) // TODO: maybe add explanation
                    },
                )
            }
        }
    }
}
