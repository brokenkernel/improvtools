package com.brokenkernel.improvtools.encyclopaedia.presentation.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.hilt.navigation.compose.hiltViewModel
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.application.presentation.view.verticalColumnScrollbar
import com.brokenkernel.improvtools.encyclopaedia.presentation.viewmodel.ThesaurusTabAllItemsViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun ThesaurusTabAllItems(viewModel: ThesaurusTabAllItemsViewModel = hiltViewModel()) {
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    val scrollState = rememberScrollState()
//    val onClickForWord
    Column(
        modifier = Modifier
            .verticalColumnScrollbar(scrollState)
            .verticalScroll(scrollState),
    ) {
        viewModel.words().forEach { word ->
            ListItem(
                headlineContent = { Text(word) },
                supportingContent = {
                    // TODO: make a table component?? antonyms?
                    Column {
                        viewModel.synonymsForWord(word).forEach { synonym ->
                            if (viewModel.hasUniqueSynonymsFrom(word, synonym)) {
                                Text(
                                    synonym,
                                    modifier = Modifier.clickable(
                                        onClick = {
                                            // TODO
                                        },
                                        onClickLabel = stringResource(
                                            R.string.go_to_single_word_thesaurus_view,
                                            synonym,
                                        ),
                                    ),
                                )
                            } else {
                                Text(
                                    synonym,
                                )
                            }
                        }
                    }
                    // TODO go to single item view when clicking on synonym
                },
                modifier = Modifier
                    .testTag("word_$word")
                    .combinedClickable(
                        onClick = {},
                        onLongClick = {
                            clipboardManager.setText(
                                AnnotatedString(
                                    """
                                |$word
                                |Synonyms: ${viewModel.synonymsForWord(word)}
                                    """.trimMargin(),
                                ),
                            )
                        },
                    ),
                overlineContent = { Text("Action") }, // todo: i18n,
//            supportingContent = TODO(),
//            leadingContent = TODO(),
//            trailingContent = TODO(),
            )
        }
    }
    // todo: consider other types of words?
    // todo: consider "action buttons". For example, "intensify" or "weaken". Also may require custom ListItem type
}
