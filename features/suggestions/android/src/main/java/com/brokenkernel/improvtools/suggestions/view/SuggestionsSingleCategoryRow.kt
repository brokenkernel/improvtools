package com.brokenkernel.improvtools.suggestions.view

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.brokenkernel.components.view.DragIconButton
import com.brokenkernel.components.view.SimpleIconButton
import com.brokenkernel.improvtools.android.R
import com.brokenkernel.improvtools.coreinfra.BottomSheetContent
import com.brokenkernel.improvtools.suggestions.data.storage.IdeaCategoryODS
import com.brokenkernel.improvtools.suggestions.data.storage.IdeaUIState
import kotlinx.collections.immutable.toImmutableList
import sh.calvin.reorderable.ReorderableCollectionItemScope

// TODO: add ability to enable/disable categories entirely persistently in settings. Maybe GridFlow to click on/off.
// TODO: maybe add single suggestion screen
// TODO: should be internal
@Composable
public fun ReorderableCollectionItemScope.SuggestionsSingleCategoryRow(
    ideaCategory: IdeaCategoryODS,
    onUpdateCategory: () -> Unit,
    onShowSingleWord: (String) -> Unit,
    onGoToEmotionTab: () -> Unit,
    setBottomSheet: (newContent: BottomSheetContent) -> Unit,
    // TODO: this shouldn't be injected, but that requires fixing thesaurus
    temporaryLoadableButton: @Composable ((String) -> Unit),
    isDragging: Boolean,
    currentIdea: IdeaUIState,
    modifier: Modifier = Modifier,
) {
    val dragScope: ReorderableCollectionItemScope = this
    val elevation by animateDpAsState(if (isDragging) 4.dp else 0.dp)

    ListItem(
        shadowElevation = elevation,
        overlineContent = { Text(ideaCategory.titleWithCount()) },
        headlineContent = { Text(currentIdea.idea) },
        modifier = modifier.clickable(
            onClick = onUpdateCategory,
            onClickLabel = stringResource(R.string.update_suggestions),
        ),
        trailingContent = {
            Row {
                if (ideaCategory.showLinkToEmotion) {
                    SimpleIconButton(
                        onClick = onGoToEmotionTab,
                        icon = ImageVector.vectorResource(
                            R.drawable.psychology_alt_24dp_1f1f1f_fill0_wght400_grad0_opsz24,
                        ),
                        contentDescription = stringResource(
                            R.string.go_to_emotions_reference,
                        ),
                    )
                }
                val currentExplanation = currentIdea.explanation
                if (currentExplanation != null) {
                    SimpleIconButton(
                        onClick = {
                            setBottomSheet({
                                ExplanationBottomSheetTab(currentIdea.idea, currentExplanation)
                            })
                        },
                        icon = ImageVector.vectorResource(
                            R.drawable.theater_comedy_24dp_1f1f1f_fill0_wght400_grad0_opsz24,
                        ),
                        contentDescription = stringResource(R.string.explain_this_term),
                    )
                }
                // TODO: none of the selected words are remembered across screens
                // TODO: this shouldn't be a viewModel but injected UIState. TBD

                // hides itself
                // TODO: FIXME
                temporaryLoadableButton(currentIdea.idea)
                SimpleIconButton(
                    onClick = {
                        setBottomSheet({
                            AllWordsBottomSheetTab(
                                ideaCategory.categoryTitle(),
                                ideaCategory.ideas.map { it.idea }.toImmutableList(),
                            )
                        })
                    },
                    icon = ImageVector.vectorResource(R.drawable.list_alt_24dp_1f1f1f_fill0_wght400_grad0_opsz24),
                    contentDescription = stringResource(R.string.suggestions_see_all_category_words),
                )
                DragIconButton(dragScope)
            }
        },
    )
}
