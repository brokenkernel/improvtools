package com.brokenkernel.components.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastAny

// TODO: There are too many variants of searchablecolumn. They should be made more ... composable

/**
 * @param T tab enumeration
 * @param I item type
 * @param X tag item type
 */
@Composable
inline fun <reified T : Enum<T>, I, reified X : Enum<X>> ChippedTabbedSearchableColumn(
    crossinline itemDoesMatch: (String, I) -> Boolean,
    itemList: Map<String, List<I>>,
    crossinline transformForSearch: (String) -> String,
    noinline itemToTopic: @Composable (I) -> T,
    noinline itemToKey: (I) -> (Any),
    noinline itemMatchesTag: (I, X) -> Boolean,
    textFieldState: TextFieldState = rememberTextFieldState(),
    noinline trailingIcon: @Composable (() -> Unit)? = null,
    isChipBarVisible: Boolean,
    crossinline itemToListItem: @Composable (I) -> (Unit), // must be last one for nice UX
) {
    Column {
        // TODO: make these a remember?
        val isSegmentedButtonChecked: SnapshotStateList<Boolean> = remember {
            MutableList(enumValues<T>().size, { true })
                .toMutableStateList()
        }
        val isChipsChecked: SnapshotStateList<Boolean> = remember {
            MutableList(enumValues<X>().size, { false })
                .toMutableStateList()
        }

        EnumLinkedMultiChoiceSegmentedButtonRow<T>(
            isSegmentedButtonChecked = isSegmentedButtonChecked,
            enumToName = { it -> it.name },
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .semantics { isTraversalGroup = true },
        ) {
            val isAnyChipSelected = isChipsChecked.fastAny { it }
            val trailingIconModifier = if (isAnyChipSelected) {
                Modifier.border(BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary))
            } else {
                Modifier
            }
            SimpleSearchBar(
                textFieldState = textFieldState,
                trailingIcon = {
                    Box(modifier = trailingIconModifier) {
                        trailingIcon?.invoke()
                    }
                },
            ) {
                if (isChipBarVisible) {
                    ChipBar<X>(
                        isChipsChecked = isChipsChecked,
                    )
                }
                LazyColumn {
                    itemList.forEach { group, groupList ->
                        if (itemList.size > 1) {
                            // TODO: consider making sticky header go away if all items are filtered out
                            // TODO: to do this, the grouping likely needs to happen after filtering?
                            stickyHeader {
                                Text(group)
                            }
                        }
                        items(
                            groupList,
                            key = itemToKey,
                        ) { curItem: I ->
                            // TODO: maybe the set of conditions should be injected? Or pulled out?
                            // this is a problem for making it composable
                            if (isSegmentedButtonChecked[itemToTopic(curItem).ordinal] &&
                                shouldShowDueToTag(isChipsChecked, itemMatchesTag, curItem) &&
                                itemDoesMatch(
                                    transformForSearch(textFieldState.text.toString()),
                                    curItem,
                                )
                            ) {
                                itemToListItem(curItem)
                            }
                        }
                    }
                }
            }
        }
    }
}

// TODO: this should be private, but kotlin is annoying about that
// TODO: also should be moved to inline function really since its just a way to simplify the if condition
inline fun <I, reified X : Enum<X>> shouldShowDueToTag(
    isChipsChecked: List<Boolean>,
    itemMatchesTag: (I, X) -> Boolean,
    curItem: I,
): Boolean {
    return isChipsChecked.all { it == false } ||
        enumValues<X>()
            .filter { tag -> itemMatchesTag(curItem, tag) }
            .fastAny { tag ->
                isChipsChecked[tag.ordinal]
            }
}
