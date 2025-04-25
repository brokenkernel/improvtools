package com.brokenkernel.improvtools.components.presentation.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics

/**
 * @param T topic type enumeration
 * @param I item type
 */
@Composable
internal inline fun <reified T : Enum<T>, I> TabbedSearchableColumn(
    crossinline itemDoesMatch: (String, I) -> Boolean,
    itemList: List<I>,
    crossinline transformForSearch: (String) -> String,
    crossinline itemToTopic: @Composable (I) -> T,
    noinline itemToKey: (I) -> (Any),
    crossinline itemToListItem: @Composable (I) -> (Unit), // must be last one for nice UX
) {
    Column {
        val textFieldState: TextFieldState = rememberTextFieldState()
        val isSingularTopic = enumValues<T>().size == 1
        val isSegmentedButtonChecked: SnapshotStateList<Boolean> =
            MutableList(enumValues<T>().size, { true })
                .toMutableStateList()
        if (!isSingularTopic) {
            EnumLinkedMultiChoiceSegmentedButtonRow<T>(
                isSegmentedButtonChecked = isSegmentedButtonChecked,
                enumToName = { it -> it.name },
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .semantics { isTraversalGroup = true },
        ) {
            SimpleSearchBar(
                textFieldState = textFieldState,
            ) {
                LazyColumn {
                    items(
                        itemList,
                        key = itemToKey,
                    ) { it: I ->
                        if (isSegmentedButtonChecked[itemToTopic(it).ordinal] &&
                            itemDoesMatch(
                                transformForSearch(textFieldState.text.toString()),
                                it,
                            )
                        ) {
                            itemToListItem(it)
                        }
//                ListItem(
//                    headlineContent = { Text("heading") },
//                    supportingContent = { Text("supporting") },
//                    leadingContent = { Text("leading") },
//                    overlineContent = { Text("overline") },
//                    trailingContent = { Text("trailing") },
//                )
                    }
                }
            }
        }
    }
}
