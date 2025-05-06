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
inline fun <reified T : Enum<T>, I> TabbedSearchableColumn(
    crossinline itemDoesMatch: (String, I) -> Boolean,
    itemList: Map<String, List<I>>,
    crossinline transformForSearch: (String) -> String,
    noinline itemToTopic: @Composable (I) -> T,
    noinline itemToKey: (I) -> (Any),
    textFieldState: TextFieldState = rememberTextFieldState(),
    noinline trailingIcon: @Composable (() -> Unit)? = null,
    crossinline itemToListItem: @Composable (I) -> (Unit), // must be last one for nice UX
) {
    Column {
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
                            if (isSegmentedButtonChecked[itemToTopic(it).ordinal] &&
                                itemDoesMatch(
                                    transformForSearch(textFieldState.text.toString()),
                                    it,
                                )
                            ) {
                                itemToListItem(it)
                            }
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
