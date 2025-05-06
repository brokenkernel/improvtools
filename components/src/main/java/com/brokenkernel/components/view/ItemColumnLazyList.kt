package com.brokenkernel.components.view

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

/**
 * @param I item type
 */
@Composable
@JvmSynthetic // hides this from Java code, thus making it private again
@PublishedApi
internal fun <I> ItemColumnLazyList(
    itemList: Map<String, List<I>>,
    itemToKey: (I) -> (Any),
    itemDoesMatch: @Composable (I) -> Boolean,
    itemToListItem: @Composable (I) -> (Unit), // must be last one for nice UX
) {
    LazyColumn {
        itemList.forEach { group, groupList ->
            // TODO: consider hiding stickyheader when all items are filtered out
            // doing so may require pushing filter outside of grouping.
            if (itemList.size > 1) {
                stickyHeader {
                    Text(group)
                }
            }
            items(
                groupList,
                key = itemToKey,
            ) { it: I ->
                if (itemDoesMatch(it)) {
                    itemToListItem(it)
                }
            }
        }
    }
}
