package com.brokenkernel.components.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics

/**
 * @param I item type
 */
@Composable
inline fun <I> SearchableColumn(
    crossinline itemDoesMatch: (String, I) -> Boolean,
    itemList: Map<String, List<I>>,
    crossinline transformForSearch: (String) -> String,
    noinline itemToKey: (I) -> (Any),
    textFieldState: TextFieldState = rememberTextFieldState(),
    noinline trailingIcon: @Composable (() -> Unit)? = null,
    crossinline itemToListItem: @Composable (I) -> (Unit), // must be last one for nice UX
) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .semantics { isTraversalGroup = true },
        ) {
            SimpleSearchBar(
                textFieldState = textFieldState,
                trailingIcon = trailingIcon,
            ) {
                LazyColumn {
                    itemList.forEach { group, groupList ->
                        if (itemList.size > 1) {
                            stickyHeader {
                                Text(group)
                            }
                        }
                        items(
                            groupList,
                            key = itemToKey,
                        ) { it: I ->
                            if (
                                itemDoesMatch(
                                    transformForSearch(textFieldState.text.toString()),
                                    it,
                                )
                            ) {
                                itemToListItem(it)
                            }
                        }
                    }
                }
            }
        }
    }
}
