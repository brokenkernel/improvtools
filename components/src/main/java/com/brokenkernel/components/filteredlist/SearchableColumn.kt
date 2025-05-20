package com.brokenkernel.components.filteredlist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import com.brokenkernel.components.view.SimpleSearchBar
import kotlinx.collections.immutable.ImmutableMap

/**
 * @param I item type
 */
@Composable
public inline fun <I> SearchableColumn(
    crossinline itemDoesMatch: (String, I) -> Boolean,
    itemList: ImmutableMap<String, List<I>>,
    crossinline transformForSearch: (String) -> String,
    noinline itemToKey: (I) -> (Any),
    modifier: Modifier = Modifier,
    noinline trailingIcon: @Composable (() -> Unit)? = null,
    noinline itemToListItem: @Composable (I) -> (Unit), // must be last one for nice UX
) {
    val textFieldState: TextFieldState = rememberTextFieldState()
    Column(modifier = modifier) {
        Box(
            Modifier
                .fillMaxSize()
                .semantics { isTraversalGroup = true },
        ) {
            SimpleSearchBar(
                text = textFieldState.text.toString(),
                onQueryChange = {
                    textFieldState.edit { replace(0, length, it) }
                },
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
