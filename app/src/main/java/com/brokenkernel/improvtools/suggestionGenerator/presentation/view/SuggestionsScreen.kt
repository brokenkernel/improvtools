package com.brokenkernel.improvtools.suggestionGenerator.presentation.view

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.application.data.model.NavigableRoute
import com.brokenkernel.improvtools.application.presentation.view.verticalColumnScrollbar
import com.brokenkernel.improvtools.suggestionGenerator.presentation.viewmodel.SuggestionScreenViewModel


@Composable
internal fun SuggestionsScreen(
    viewModel: SuggestionScreenViewModel = hiltViewModel(),
    onNavigateToRoute: (NavigableRoute) -> Unit,
) {
// TODO: add info icon - general

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Text(
                text = stringResource(R.string.suggestions_title_category),
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .border(1.dp, MaterialTheme.colorScheme.tertiary)
                    .fillMaxWidth(.3f),
                style = MaterialTheme.typography.titleLarge,
            )
            Text(
                text = stringResource(R.string.suggestions_audience_idea),
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .border(1.dp, MaterialTheme.colorScheme.tertiary)
                    .fillMaxWidth(),
                style = MaterialTheme.typography.titleLarge,
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .weight(11f)
        ) {
            // should these all be ListItem instead??
            val scrollState: ScrollState = rememberScrollState()
            // TODO add sortable?
            Column(
                modifier = Modifier
                    .verticalColumnScrollbar(scrollState)
                    .verticalScroll(scrollState)
                    .fillMaxWidth()
            ) {
                viewModel.internalCategoryDatum.forEach { ideaCategory ->
                    val itemSuggestionState: State<String>? =
                        viewModel.categoryDatumToSuggestion[ideaCategory]?.collectAsState()
                    ListItem(
                        overlineContent = { Text(ideaCategory.titleWithCount()) },
                        headlineContent = { Text(itemSuggestionState?.value.orEmpty()) },
                        modifier = Modifier.clickable(
                            onClick = {
                                viewModel.updateSuggestionXFor(ideaCategory)
                            },
                        ),
                        trailingContent = {
                            if (ideaCategory.showLinkToEmotion) {
                                IconButton(
                                    onClick = {
                                        onNavigateToRoute(NavigableRoute.EmotionPageRoute)
                                    },
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Icon(
                                        Icons.Default.Info,
                                        contentDescription = "TODO",
                                    )
                                }
                            }
                        }
                    )
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
            // maybe floating action button?
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
