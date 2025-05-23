package com.brokenkernel.improvtools.encyclopaedia.presentation.view

import androidx.annotation.StringRes
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.brokenkernel.components.view.HtmlText
import com.brokenkernel.components.view.verticalColumnScrollbar
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.application.navigation.ImprovToolsDestination
import com.brokenkernel.improvtools.components.sidecar.navigation.ImprovToolsNavigationGraph
import com.brokenkernel.improvtools.encyclopaedia.presentation.viewmodel.ThesaurusSingleItemViewModel
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@ImprovToolsDestination<ImprovToolsNavigationGraph>
@Composable
internal fun ThesaurusTabSingleWord(
    word: String,
    navigator: DestinationsNavigator,
    @StringRes priorTitleResource: Int,
    viewModel: ThesaurusSingleItemViewModel = hiltViewModel(),
) {
    val scrollState: ScrollState = rememberScrollState()

    SelectionContainer {
        Column(
            modifier = Modifier
                .verticalColumnScrollbar(scrollState)
                .verticalScroll(scrollState),
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    word,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                )
            }
            if (viewModel.shouldShowActionSynonyms(word)) {
                HtmlText(stringResource(R.string.encyclopaedia_action_synonyms))
                HtmlText(viewModel.renderedActionSynonyms(word))
            }
            val wordSenseRenderedString = viewModel.renderedWordSenses(word)
            HtmlText(stringResource(R.string.encyclopaedia_word_senses))
            HtmlText(wordSenseRenderedString)
            ExtendedFloatingActionButton(
                onClick = navigator::popBackStack,
            ) {
                val backtoText =
                    stringResource(R.string.navigation_back_to_thesaurus, stringResource(priorTitleResource))
                Icon(
                    Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = backtoText,
                )
                Text(backtoText)
            }
        }
    }
}
