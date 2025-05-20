package com.brokenkernel.components.filteredlist

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.collections.immutable.ImmutableMap

/**
 * @param I item type
 */
@Composable
@JvmSynthetic // hides this from Java code, thus making it private again
@PublishedApi
internal fun <I> ItemColumnLazyList(
    itemList: ImmutableMap<String, List<I>>,
    itemToKey: (I) -> (Any),
    itemDoesMatch: (I) -> Boolean,
    modifier: Modifier = Modifier,
    itemToListItem: @Composable (I) -> (Unit), // must be last one for nice UX
) {
    LazyColumn(modifier = modifier) {
        val filteredGroupedList = itemList
            .mapValues { (_, value) ->
                value.filter { itemDoesMatch(it) }
            }
        filteredGroupedList.forEach { group, groupList ->
            if (groupList.size > 1) {
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
