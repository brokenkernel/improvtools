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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.application.presentation.view.verticalColumnScrollbar
import com.brokenkernel.improvtools.components.presentation.view.HtmlText
import com.brokenkernel.improvtools.encyclopaedia.presentation.viewmodel.ThesaurusSingleItemViewModel

@Composable
internal fun ThesaurusTabSingleWord(
    word: String,
    viewModel: ThesaurusSingleItemViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    onLaunchTitleCallback: () -> Unit,
    @StringRes priorTitleResource: Int,
) {
    // TODO: consider making a BaseScreenComposable or some such
    LaunchedEffect(Unit) {
        onLaunchTitleCallback()
    }

    val scrollState: ScrollState = rememberScrollState()

    SelectionContainer {
        Column(modifier = Modifier.verticalColumnScrollbar(scrollState).verticalScroll(scrollState)) {
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
            HtmlText("<h1>Action Synonyms</h1>")
            viewModel.synonymsForWord(word).forEach { synonym ->
                Text(synonym)
            }
            val wordSenseRenderedString = viewModel.getWordSensesFullyRenderedString(word)
            if (wordSenseRenderedString != null) {
                HtmlText("<h1>Word Senses</h1>")
                HtmlText(wordSenseRenderedString)
            }
            ExtendedFloatingActionButton(
                onClick = {
                    onNavigateBack()
                },
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
