package com.brokenkernel.components.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastAny
import androidx.compose.ui.util.fastFilter
import com.brokenkernel.components.R
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableList

// TODO: There are too many variants of searchablecolumn. They should be made more ... composable

/**
 * @param T tab enumeration
 * @param X tag item type
 */
data class ChippedTabbedSearchableColumn<T, X>(
    val isSegmentedButtonChecked: SnapshotStateList<Boolean>,
    val isChipsChecked: SnapshotStateList<Boolean>,
)

/**
 * @param T tab enumeration
 * @param X tag item type
 */
@Composable
inline fun <reified T : Enum<T>, reified X : Enum<X>> rememberChippedTabbedSearchableColumn():
    ChippedTabbedSearchableColumn<T, X> {
    return remember {
        ChippedTabbedSearchableColumn<T, X>(
            isSegmentedButtonChecked = MutableList(enumValues<T>().size, { true })
                .toMutableStateList(),
            isChipsChecked = MutableList(enumValues<X>().size, { false })
                .toMutableStateList(),
        )
    }
}

/**
 * @param T tab enumeration
 * @param I item type
 * @param X tag item type
 */
@Composable
inline fun <reified T : Enum<T>, I, reified X : Enum<X>> ChippedTabbedSearchableColumn(
    crossinline itemDoesMatch: (String, I) -> Boolean,
    itemList: ImmutableMap<String, List<I>>,
    crossinline transformForSearch: (String) -> String,
    noinline itemToTopic: (I) -> T,
    noinline itemToKey: (I) -> (Any),
    noinline itemMatchesTag: (I, X) -> Boolean,
    isChipBarVisible: Boolean,
    modifier: Modifier = Modifier,
    state: ChippedTabbedSearchableColumn<T, X> = rememberChippedTabbedSearchableColumn<T, X>(),
    noinline trailingIcon: @Composable (() -> Unit)? = null,
    textFieldState: TextFieldState = rememberTextFieldState(),
    noinline itemToListItem: @Composable (I) -> (Unit), // must be last one for nice UX
) {
    Column(modifier = modifier) {
        EnumLinkedMultiChoiceSegmentedButtonRow<T>(
            isSegmentedButtonChecked = state.isSegmentedButtonChecked.toImmutableList(),
            onSegmentClick = {
                state.isSegmentedButtonChecked[it] = !state.isSegmentedButtonChecked[it]
            },
            enumToName = { it -> it.name },
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .semantics { isTraversalGroup = true },
        ) {
            val isAnyChipSelected = state.isChipsChecked.fastAny { it }
            val colorScheme = MaterialTheme.colorScheme
            val trailingIconModifier = if (isAnyChipSelected) {
                Modifier.drawWithContent {
                    drawContent()
                    drawNeonStroke(radius = 8.dp, colorScheme.tertiary)
                }
            } else {
                Modifier
            }
            SimpleSearchBar(
                textFieldState = textFieldState,
                trailingIcon = {
                    val selectedCount = state.isChipsChecked.fastFilter { it }.size
                    SimpleTooltipWrapper(
                        tooltip = stringResource(R.string.tags_selected, selectedCount),
                    ) {
                        Box(
                            modifier = trailingIconModifier,
                        ) {
                            trailingIcon?.invoke()
                        }
                    }
                },
            ) {
                Column {
                    AnimatedVisibility(isChipBarVisible) {
                        ChipBar<X>(
                            isChipsChecked = state.isChipsChecked.toImmutableList(),
                            onChipClick = {
                                state.isChipsChecked[it] = !state.isChipsChecked[it]
                            },
                            onClearClick = {
                                for (i in 0 until state.isChipsChecked.size) {
                                    state.isChipsChecked[i] = false
                                }
                            },
                        )
                    }
                    ItemColumnLazyList<I>(
                        itemList,
                        itemToKey,
                        { it ->
                            (
                                state.isSegmentedButtonChecked[itemToTopic(it).ordinal] &&
                                    shouldShowDueToTag(state.isChipsChecked, itemMatchesTag, it) &&
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
