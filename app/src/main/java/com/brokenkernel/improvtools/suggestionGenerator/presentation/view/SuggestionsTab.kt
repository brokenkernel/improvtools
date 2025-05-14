package com.brokenkernel.improvtools.suggestionGenerator.presentation.view

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.outlined.PsychologyAlt
import androidx.compose.material.icons.outlined.TheaterComedy
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
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
import com.brokenkernel.components.view.SimpleIconButton
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.application.data.model.ImprovToolsAppState
import com.brokenkernel.improvtools.application.navigation.ImprovToolsDestination
import com.brokenkernel.improvtools.components.presentation.view.DragIconButton
import com.brokenkernel.improvtools.components.sidecar.navigation.ImprovToolsNavigationGraph
import com.brokenkernel.improvtools.encyclopaedia.presentation.view.LoadableSingleWordThesaurusButton
import com.brokenkernel.improvtools.suggestionGenerator.data.model.IdeaCategoryODS
import com.brokenkernel.improvtools.suggestionGenerator.data.model.IdeaUIState
import com.brokenkernel.improvtools.suggestionGenerator.presentation.viewmodel.SuggestionScreenViewModel
import com.ramcosta.composedestinations.generated.destinations.EmotionTabDestination
import com.ramcosta.composedestinations.generated.destinations.ThesaurusTabSingleWordDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import sh.calvin.reorderable.ReorderableCollectionItemScope
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyListState

// TODO: add ability to enable/disable categories entirely persistently in settings. Maybe GridFlow to click on/off.
// TODO: maybe add single suggestion screen

@OptIn(ExperimentalMaterial3Api::class)
@ImprovToolsDestination<ImprovToolsNavigationGraph>(start = true)
@Composable
internal fun SuggestionsTab(
    navigator: DestinationsNavigator,
    improvToolsAppState: ImprovToolsAppState,
    viewModel: SuggestionScreenViewModel = hiltViewModel(),
) {
    val onNavigateToExplanation: (String, String) -> Unit = { word: String, explanation: String ->
        improvToolsAppState.setBottomSheetTo {
            ExplanationBottomSheetTab(word, explanation)
        }
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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(11f),
            ) {
                // TODO: maybe this should live in viewModel instead of in composable
                // TODO: should also be saved across edits and persisted?
                val reorderedListOfSuggestions: List<IdeaCategoryODS> = remember {
                    viewModel.internalCategoryDatum
                }
                val lazyListState = rememberLazyListState()
                val reorderableLazyListState = rememberReorderableLazyListState(lazyListState) { from, to ->
                    viewModel.internalCategoryDatum.apply {
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
                    items(reorderedListOfSuggestions, key = IdeaCategoryODS::itemKey) { ideaCategory: IdeaCategoryODS ->
                        ReorderableItem(
                            state = reorderableLazyListState,
                            key = ideaCategory.itemKey(),
                        ) { isDragging ->
                            val dragScope: ReorderableCollectionItemScope = this
                            val elevation by animateDpAsState(if (isDragging) 4.dp else 0.dp)

                            val itemSuggestionState: State<IdeaUIState> =
                                viewModel.categoryDatumToSuggestion.getValue(ideaCategory).collectAsStateWithLifecycle()
                            val currentIdea: IdeaUIState = itemSuggestionState.value
                            ListItem(
                                shadowElevation = elevation,
                                overlineContent = { Text(ideaCategory.titleWithCount()) },
                                headlineContent = { Text(currentIdea.idea) },
                                modifier = Modifier.clickable(
                                    onClick = {
                                        viewModel.updateSuggestionXFor(ideaCategory)
                                    },
                                    onClickLabel = stringResource(R.string.update_suggestion),
                                ),
                                trailingContent = {
                                    Row {
                                        if (ideaCategory.showLinkToEmotion) {
                                            SimpleIconButton(
                                                onClick = {
                                                    navigator.navigate(EmotionTabDestination)
                                                },
                                                icon = Icons.Outlined.PsychologyAlt,
                                                contentDescription = stringResource(
                                                    R.string.go_to_emotions_reference_screen,
                                                ),
                                            )
                                        }
                                        if (currentIdea.explanation != null) {
                                            SimpleIconButton(
                                                onClick = {
                                                    onNavigateToExplanation(
                                                        currentIdea.idea,
                                                        currentIdea.explanation,
                                                    )
                                                },
                                                icon = Icons.Outlined.TheaterComedy,
                                                contentDescription = stringResource(
                                                    R.string.explain_this_term,
                                                ),
                                            )
                                        }
                                        // TODO: none of the selected words are remembered across screens
                                        // TODO: this shouldn't be a viewModel but injected UIState. TBD

                                        LoadableSingleWordThesaurusButton(
                                            word = currentIdea.idea,
                                            onNavigateToWord = {
                                                navigator.navigate(
                                                    ThesaurusTabSingleWordDestination(
                                                        currentIdea.idea,
                                                        improvToolsAppState.currentTitle.value,
                                                    ),
                                                )
                                            },
                                            whenDisabledFullyHidden = true,
                                        )
                                        DragIconButton(dragScope)
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
