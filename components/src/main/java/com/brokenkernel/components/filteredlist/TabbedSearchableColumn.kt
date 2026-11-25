package com.brokenkernel.components.filteredlist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.SearchBarValue
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLocale
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.intl.Locale
import com.brokenkernel.components.view.SimpleSearchBar
import kotlin.enums.enumEntries
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch

public data class TabbedSearchableColumnState<T : Enum<T>>(
    val isSegmentedButtonChecked: SnapshotStateList<Boolean>,
)

@Composable
public inline fun <reified T : Enum<T>> rememberTabbedSearchableColumnState(): TabbedSearchableColumnState<T> {
    return remember {
        TabbedSearchableColumnState<T>(
            isSegmentedButtonChecked = MutableList(enumEntries<T>().size, { true })
                .toMutableStateList(),
        )
    }
}

/**
 * @param T tab enumeration
 * @param I item type
 */
@Composable
public inline fun <reified T : Enum<T>, I> TabbedSearchableColumn(
    crossinline itemDoesMatch: (Locale, String, I) -> Boolean,
    itemList: ImmutableMap<String, List<I>>,
    crossinline transformForSearch: (String) -> String,
    noinline itemToTopic: (I) -> T,
    noinline itemToKey: (I) -> (Any),
    modifier: Modifier = Modifier,
    state: TabbedSearchableColumnState<T> = rememberTabbedSearchableColumnState(),
    textFieldState: TextFieldState = rememberTextFieldState(),
    noinline trailingIcon: @Composable (() -> Unit)? = null,
    noinline itemToListItem: @Composable (I) -> (Unit), // must be last one for nice UX
) {
    val scope = rememberCoroutineScope()
    val searchBarState = rememberSearchBarState(
        initialValue = SearchBarValue.Expanded,
    )

    Column(modifier = modifier) {
        EnumLinkedMultiChoiceSegmentedButtonRow<T>(
            isSegmentedButtonChecked = state.isSegmentedButtonChecked.toImmutableList(),
            onSegmentClick = {
                state.isSegmentedButtonChecked[it] = !state.isSegmentedButtonChecked[it]
                scope.launch { searchBarState.animateToExpanded() }
            },
            enumToName = { it -> it.name },
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .semantics { isTraversalGroup = true },
        ) {
            SimpleSearchBar(
                textFieldState = textFieldState,
                searchBarState = searchBarState,
                trailingIcon = trailingIcon,
                content = {
                    val locale = LocalLocale.current
                    ItemColumnLazyList<I>(
                        itemList,
                        itemToKey,
                        { it ->
                            (
                                state.isSegmentedButtonChecked[itemToTopic(it).ordinal] &&
                                    itemDoesMatch(
                                        locale,
                                        transformForSearch(textFieldState.text.toString()),
                                        it,
                                    )
                                )
                        },
                        itemToListItem = itemToListItem,
                    )
                },
            )
        }
    }
}
