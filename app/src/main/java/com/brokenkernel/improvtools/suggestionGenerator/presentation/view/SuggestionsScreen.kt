package com.brokenkernel.improvtools.suggestionGenerator.presentation.view

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChangeCircle
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.application.presentation.view.verticalColumnScrollbar
import com.brokenkernel.improvtools.suggestionGenerator.presentation.viewmodel.SuggestionScreenViewModel


@Preview
@Composable
internal fun SuggestionsScreen(
) {
    LoadableScreen(
        loader = {}
    ) {
        SuggestionsScreenFullyLoaded()
    }
}


@Composable
internal fun SuggestionsScreenFullyLoaded(viewModel: SuggestionScreenViewModel = hiltViewModel()) {

    val categoryWeight = .3f
    val audienceIdeaWeight = .7f

    // assert total is 100.

    // TODO: the weight are kind of random below. I'm not actually sure why they work. Figure it out.
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        // Header
        Row(
            Modifier
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .weight(1f)
                .fillMaxWidth()
        ) {
            TableCell(
                text = stringResource(R.string.suggestions_title_category),
                weight = categoryWeight,
                style = MaterialTheme.typography.titleLarge,
            )
            TableCell(
                text = stringResource(R.string.suggestions_audience_idea),
                weight = audienceIdeaWeight,
                style = MaterialTheme.typography.titleLarge,
            )
        }
        Row(
            modifier = Modifier
                .fillMaxSize()
                .weight(10f)
        ) {
            val scrollState: ScrollState = rememberScrollState()
            // TODO add sortable?
            Column(modifier = Modifier.verticalColumnScrollbar(scrollState).verticalScroll(scrollState)) {
                viewModel.internalCategoryDatum.forEach { ideaCategory ->
                    val itemSuggestionState: State<String>? =
                        viewModel.categoryDatumToSuggestion[ideaCategory]?.collectAsState()
                    Row(
                        modifier = Modifier
                            .height(IntrinsicSize.Max),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TableCell(
                            text = ideaCategory.titleWithCount(),
                            weight = categoryWeight,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                        ClickableTableCell(
                            text = itemSuggestionState?.value.orEmpty(),
                            weight = audienceIdeaWeight,
                            style = MaterialTheme.typography.bodyMedium,
                            onClick = {
                                viewModel.updateSuggestionXFor(ideaCategory)
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
                .weight(1f)
        ) {
            FilledTonalButton(
                onClick = { viewModel.resetAllCategories() },

                ) {
                Row {
                    Icon(
                        Icons.Filled.ChangeCircle,
                        contentDescription = stringResource(R.string.suggestions_reset_all)
                    )
                    Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_icon_text_for_button)))
                    Text(text = stringResource(R.string.suggestions_reset_all))
                }
            }
        }
    }
}
