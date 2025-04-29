package com.brokenkernel.improvtools.suggestionGenerator.presentation.view

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChangeCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.rounded.DragHandle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.encyclopaedia.presentation.view.SingleWordThesaurusButton
import com.brokenkernel.improvtools.suggestionGenerator.data.model.IdeaCategory
import com.brokenkernel.improvtools.suggestionGenerator.presentation.viewmodel.SuggestionScreenViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyListState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SuggestionsTab(
    viewModel: SuggestionScreenViewModel = hiltViewModel(),
    onNavigateToEmotionsInfographic: () -> Unit,
    onNavigateToWord: (String) -> Unit,
    onLaunchTitleCallback: () -> Unit,
) {
    // TODO: consider making a BaseScreenComposable or some such
    LaunchedEffect(Unit) {
        onLaunchTitleCallback()
    }

    val state = rememberPullToRefreshState()
    var isRefreshing by rememberSaveable { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val haptic = LocalHapticFeedback.current

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        state = state,
        onRefresh = {
            coroutineScope.launch {
                isRefreshing = true
                viewModel.resetAllCategories()
                // https://issuetracker.google.com/issues/248274004
                delay(100L)
                isRefreshing = false
            }
        },
        modifier = Modifier.testTag("OutermostContentForSuggestionsScreen"),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(11f),
            ) {
                // TODO: maybe this should live in viewModel instead of in composable
                // TODO: should also be saved across edits and persisted?
                var reorderedListOfSuggestions: List<IdeaCategory> by remember {
                    mutableStateOf(viewModel.internalCategoryDatum)
                }
                val lazyListState = rememberLazyListState()
                val reorderableLazyListState = rememberReorderableLazyListState(lazyListState) { from, to ->
                    reorderedListOfSuggestions = reorderedListOfSuggestions.toMutableList().apply {
                        val fromIndex = indexOfFirst { it.itemKey() == from.key }
                        val toIndex = indexOfFirst { it.itemKey() == to.key }

                        add(toIndex, removeAt(fromIndex))
                        add(to.index, removeAt(from.index))
                    }
                    haptic.performHapticFeedback(HapticFeedbackType.SegmentFrequentTick)
                }

                // TODO add sortable?
                LazyColumn(
                    state = lazyListState,
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    items(reorderedListOfSuggestions, key = IdeaCategory::itemKey) { ideaCategory ->
                        ReorderableItem(state = reorderableLazyListState, key = IdeaCategory::itemKey) { isDragging ->
                            val elevation by animateDpAsState(if (isDragging) 4.dp else 0.dp)

                            val itemSuggestionState: State<String>? =
                                viewModel.categoryDatumToSuggestion[ideaCategory]?.collectAsStateWithLifecycle()
                            val currentWord = itemSuggestionState?.value.orEmpty()
                            ListItem(
                                shadowElevation = elevation,
                                overlineContent = { Text(ideaCategory.titleWithCount()) },
                                headlineContent = { Text(currentWord) },
                                modifier = Modifier.clickable(
                                    onClick = {
                                        viewModel.updateSuggestionXFor(ideaCategory)
                                    },
                                ),
                                trailingContent = {
                                    Row(modifier = Modifier.weight(1f)) {
                                        if (ideaCategory.showLinkToEmotion) {
                                            IconButton(
                                                onClick = {
                                                    onNavigateToEmotionsInfographic()
                                                },
                                            ) {
                                                Icon(
                                                    Icons.Default.Info,
                                                    contentDescription = stringResource(
                                                        R.string.go_to_emotions_reference_screen,
                                                    ),
                                                )
                                            }
                                        }

                                        // TODO: look up words and show details, not just the few I know of
                                        if (viewModel.isWordInThesaurus(currentWord)) {
                                            // TODO: consider pop up menu instead of full screen
                                            // TODO: none of the selected words are remembered across screens
                                            SingleWordThesaurusButton(
                                                word = currentWord,
                                                onNavigateToWord = onNavigateToWord,
                                            )
                                        }
                                        IconButton(
                                            modifier = Modifier.longPressDraggableHandle(
                                                onDragStarted = {
                                                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                                },
                                                onDragStopped = {
                                                    haptic.performHapticFeedback(HapticFeedbackType.GestureEnd)
                                                },
                                            ),
                                            onClick = {},
                                        ) {
                                            Icon(Icons.Rounded.DragHandle, contentDescription = "Reorder")
                                        }
                                    }
                                },
                            )
                        }
                    }
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            ) {
                // maybe floating action button?
                FilledTonalButton(
                    onClick = { viewModel.resetAllCategories() },
                ) {
                    Row {
                        Icon(
                            Icons.Filled.ChangeCircle,
                            contentDescription = stringResource(R.string.suggestions_reset_all),
                        )
                        Spacer(
                            modifier = Modifier.padding(
                                dimensionResource(R.dimen.padding_icon_text_for_button),
                            ),
                        )
                        Text(text = stringResource(R.string.suggestions_reset_all))
                    }
                }
            }
        }
    }
}
