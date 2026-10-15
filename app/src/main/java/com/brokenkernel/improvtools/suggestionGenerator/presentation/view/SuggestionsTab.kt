package com.brokenkernel.improvtools.suggestionGenerator.presentation.view

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.EntryProviderScope
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.application.data.model.NavigableScreens
import com.brokenkernel.improvtools.coreinfra.BottomSheetContent
import com.brokenkernel.improvtools.coreinfra.BottomSheetSceneStrategy
import com.brokenkernel.improvtools.coreinfra.ImprovToolsNavigationKey
import com.brokenkernel.improvtools.coreinfra.ImprovToolsNavigator
import com.brokenkernel.improvtools.encyclopaedia.EncyclopaediaSectionNavigation
import com.brokenkernel.improvtools.encyclopaedia.android.api.EmotionsPageNavigationKey
import com.brokenkernel.improvtools.encyclopaedia.android.thesaurus.view.LoadableSingleWordThesaurusButton
import com.brokenkernel.improvtools.suggestions.api.SuggestionsAllWordsForNavigationKey
import com.brokenkernel.improvtools.suggestions.api.SuggestionsScreenNavigationKey
import com.brokenkernel.improvtools.suggestions.data.storage.IdeaCategoryODS
import com.brokenkernel.improvtools.suggestions.data.storage.IdeaUIState
import com.brokenkernel.improvtools.suggestions.view.SuggestionsSingleCategoryRow
import com.brokenkernel.improvtools.suggestions.viewmodel.SuggestionScreenViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyListState
import kotlin.time.Duration.Companion.milliseconds

// TODO: add ability to enable/disable categories entirely persistently in settings. Maybe GridFlow to click on/off.
// TODO: maybe add single suggestion screen
@Composable
internal fun SuggestionsTab(
    setBottomSheet: (BottomSheetContent) -> Unit,
    goTo: (ImprovToolsNavigationKey) -> Unit,
    improvToolsNavigator: ImprovToolsNavigator,
    modifier: Modifier = Modifier,
    viewModel: SuggestionScreenViewModel = hiltViewModel(),
) {
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
                delay(100L.milliseconds)
                isRefreshing = false
            }
        },
        modifier = modifier.testTag("OutermostContentForSuggestionsScreen"),
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
                val reorderableLazyListState =
                    rememberReorderableLazyListState(lazyListState) { from, to ->
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
                    items(
                        reorderedListOfSuggestions,
                        key = IdeaCategoryODS::categoryTitle,
                    ) { ideaCategory: IdeaCategoryODS ->
                        ReorderableItem(
                            state = reorderableLazyListState,
                            key = ideaCategory.categoryTitle(),
                        ) { isDragging ->
                            val itemSuggestionState: State<IdeaUIState> =
                                viewModel.categoryDatumToSuggestion.getValue(ideaCategory)
                                    .collectAsStateWithLifecycle()

                            SuggestionsSingleCategoryRow(
                                ideaCategory = ideaCategory,
                                onUpdateCategory = {
                                    viewModel.updateSuggestionXFor(ideaCategory)
                                },
                                onShowSingleWord = {
                                    EncyclopaediaSectionNavigation.navigateToThesaurusWord(
                                        backstack = improvToolsNavigator.backstack,
                                        itemSuggestionState.value.idea,
                                        NavigableScreens.byRoute(improvToolsNavigator.current()).titleResource,
                                    )
                                },
                                onGoToEmotionTab = {
                                    goTo(EmotionsPageNavigationKey)
                                },
                                setBottomSheet = setBottomSheet,
                                goTo = goTo,
                                temporaryLoadableButton = { word: String ->
                                    LoadableSingleWordThesaurusButton(
                                        word = itemSuggestionState.value.idea,
                                        onNavigateToWord = {
                                            EncyclopaediaSectionNavigation.navigateToThesaurusWord(
                                                improvToolsNavigator.backstack,
                                                word,
                                                NavigableScreens.byRoute(improvToolsNavigator.current()).titleResource,
                                            )
                                        },
                                        whenDisabledFullyHidden = true,
                                    )
                                },
                                isDragging = isDragging,
                                currentIdea = itemSuggestionState.value,
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
                            ImageVector.vectorResource(R.drawable.change_circle_24dp_1f1f1f_fill0_wght400_grad0_opsz24),
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

@OptIn(ExperimentalMaterial3Api::class)
internal fun EntryProviderScope<ImprovToolsNavigationKey>.suggestionsScreenEntryBuilder(
    setBottomSheet: (BottomSheetContent) -> Unit,
    improvToolsNavigator: ImprovToolsNavigator,
) {
    entry<SuggestionsScreenNavigationKey> {
        SuggestionsTab(
            setBottomSheet = setBottomSheet,
            goTo = improvToolsNavigator::goTo,
            improvToolsNavigator,
        )
    }
    entry<SuggestionsAllWordsForNavigationKey>(
        metadata = BottomSheetSceneStrategy.bottomSheet(),
    ) { route ->
        SuggestionsAllWordsForNavigationKey(
            header = route.header,
            words = route.words
        )
    }

}
