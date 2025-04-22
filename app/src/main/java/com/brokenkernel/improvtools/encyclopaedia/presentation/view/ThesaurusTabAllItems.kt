package com.brokenkernel.improvtools.encyclopaedia.presentation.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.hilt.navigation.compose.hiltViewModel
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.application.data.model.ImprovToolsAppState
import com.brokenkernel.improvtools.application.presentation.view.verticalColumnScrollbar
import com.brokenkernel.improvtools.encyclopaedia.EncyclopaediaSectionNavigation
import com.brokenkernel.improvtools.encyclopaedia.presentation.viewmodel.ThesaurusTabAllItemsViewModel
import androidx.compose.foundation.lazy.items

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun ThesaurusTabAllItems(
    improvToolsAppState: ImprovToolsAppState,
    viewModel: ThesaurusTabAllItemsViewModel = hiltViewModel(),
) {
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    LazyColumn {
        items(viewModel.words()) { word ->
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
                                            EncyclopaediaSectionNavigation.navigateToThesaurusWord(
                                                improvToolsAppState,
                                                synonym,
                                            )
                                        },
                                        onClickLabel = stringResource(
                                            R.string.go_to_single_word_thesaurus_view,
                                            synonym,
                                        ),
                                    ),
                                    style = TextStyle.Default.copy(
                                        color = MaterialTheme.colorScheme.primary,
                                        textDecoration = TextDecoration.Underline,
                                    ),
                                )
                            } else {
                                Text(
                                    synonym,
                                )
                            }
                        }
                    }
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
//            leadingContent = TODO(),
//            trailingContent = TODO(),
            )
        }
    }
    // todo: consider other types of words?
    // todo: consider "action buttons". For example, "intensify" or "weaken". Also may require custom ListItem type
}
