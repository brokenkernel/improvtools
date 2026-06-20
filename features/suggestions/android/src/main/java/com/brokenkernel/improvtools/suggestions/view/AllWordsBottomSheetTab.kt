package com.brokenkernel.improvtools.suggestions.view

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.collections.immutable.ImmutableList

// TODO: should be internal
@Composable
public fun AllWordsBottomSheetTab(
    words: ImmutableList<String>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier) {
        items(words) { word ->
            ListItem(
                headlineContent = {
                    Text(word) // TODO: maybe add explanation
                },
            )
        }
    }
}
