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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.suggestionGenerator.data.model.SuggestionCategory
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
    val audienceSuggestionUIState by viewModel.uiState.collectAsState()

    val categoryWeight = .3f
    val audienceIdeaWeight = .7f
    val scrollState: ScrollState = rememberScrollState()
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
        Row(modifier = Modifier.fillMaxSize().weight(10f)) {
            Column(modifier = Modifier.verticalScroll(scrollState)) {
                SuggestionCategory.entries.forEach { suggestionData ->
                    Row(
                        modifier = Modifier
                            .height(IntrinsicSize.Max),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TableCell(
                            text = suggestionData.title,
                            weight = categoryWeight,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                        ClickableTableCell(
                            text = audienceSuggestionUIState.audienceSuggestions.getOrDefault(
                                suggestionData,
                                "unknown"
                            ), //  TODO I shouldn't have to do this. Maybe EnumMap
                            weight = audienceIdeaWeight,
                            style = MaterialTheme.typography.bodyMedium,
                            onClick = {
                                viewModel.updateSuggestionFor(suggestionData)
                            }
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
