package com.brokenkernel.components.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics

data class TabbedSearchableColumnState<_T>(
    val isSegmentedButtonChecked: SnapshotStateList<Boolean>,
)

@Composable
inline fun <reified T : Enum<T>> rememberTabbedSearchableColumnState(): TabbedSearchableColumnState<T> {
    return remember {
        TabbedSearchableColumnState<T>(
            isSegmentedButtonChecked = MutableList(enumValues<T>().size, { true })
                .toMutableStateList(),
        )
    }
}

/**
 * @param T tab enumeration
 * @param I item type
 */
@Composable
inline fun <reified T : Enum<T>, I> TabbedSearchableColumn(
    state: TabbedSearchableColumnState<T> = rememberTabbedSearchableColumnState<T>(),
    crossinline itemDoesMatch: (String, I) -> Boolean,
    itemList: Map<String, List<I>>,
    crossinline transformForSearch: (String) -> String,
    noinline itemToTopic: @Composable (I) -> T,
    noinline itemToKey: (I) -> (Any),
    textFieldState: TextFieldState = rememberTextFieldState(),
    noinline trailingIcon: @Composable (() -> Unit)? = null,
    noinline itemToListItem: @Composable (I) -> (Unit), // must be last one for nice UX
) {
    Column {
        EnumLinkedMultiChoiceSegmentedButtonRow<T>(
            isSegmentedButtonChecked = state.isSegmentedButtonChecked,
            enumToName = { it -> it.name },
        )
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
                        (
                            state.isSegmentedButtonChecked[itemToTopic(it).ordinal] &&
                                itemDoesMatch(
                                    transformForSearch(textFieldState.text.toString()),
                                    it,
                                )
                            )
                    },
                    itemToListItem = itemToListItem,
                )
            }
        }
    }
}
