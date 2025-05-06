package com.brokenkernel.components.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
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
    noinline itemToListItem: @Composable (I) -> (Unit), // must be last one for nice UX
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
                ItemColumnLazyList<I>(
                    itemList,
                    itemToKey,
                    { it ->
                        itemDoesMatch(
                            transformForSearch(textFieldState.text.toString()),
                            it,
                        )
                    },
                    itemToListItem = itemToListItem,
                )
            }
        }
    }
}
