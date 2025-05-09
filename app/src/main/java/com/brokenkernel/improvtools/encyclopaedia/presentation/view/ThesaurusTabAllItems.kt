package com.brokenkernel.improvtools.encyclopaedia.presentation.view

import android.content.ClipData
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.Clipboard
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.platform.toClipEntry
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.hilt.navigation.compose.hiltViewModel
import com.brokenkernel.components.view.SearchableColumn
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.application.data.model.ImprovToolsAppState
import com.brokenkernel.improvtools.encyclopaedia.EncyclopaediaSectionNavigation
import com.brokenkernel.improvtools.encyclopaedia.presentation.viewmodel.LoadableSingleWordThesaurusButtonViewModel
import com.brokenkernel.improvtools.encyclopaedia.presentation.viewmodel.ThesaurusTabAllItemsViewModel
import kotlinx.coroutines.launch

@Composable
internal fun ThesaurusTabAllItems(
    improvToolsAppState: ImprovToolsAppState,
    viewModel: ThesaurusTabAllItemsViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val clipboard: Clipboard = LocalClipboard.current
    val coroutineScope = rememberCoroutineScope()
    SearchableColumn<String>(
        itemDoesMatch = { searchText, word -> word.contains(searchText, ignoreCase = true) },
        itemList = viewModel.groupedWords(),
        transformForSearch = { s -> s },
        itemToKey = { it -> it },
    ) { word ->
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
            trailingContent = {
                val viewModel =
                    hiltViewModel<
                        LoadableSingleWordThesaurusButtonViewModel,
                        LoadableSingleWordThesaurusButtonViewModel.Factory,
                        >(
                        key = word,
                        creationCallback = { factory ->
                            factory.create(word)
                        },
                    )
                LoadableSingleWordThesaurusButton(
                    viewModel = viewModel,
                    onNavigateToWord = {
                        EncyclopaediaSectionNavigation.navigateToThesaurusWord(
                            improvToolsAppState,
                            word,
                        )
                    },
                )
            },
            modifier = Modifier
                .testTag("word_$word")
                .combinedClickable(
                    onClick = {},
                    onLongClick = {
                        val wordString = AnnotatedString(
                            """
                                |$word
                                |${context.getString(R.string.thesaurus_synonyms, viewModel.synonymsForWord(word))}
                            """.trimMargin(),
                        )
                        val clipData = ClipData.newPlainText(
                            context.getString(R.string.thesaurus_word_and_synonyms),
                            wordString,
                        )
                        val clipEntry = clipData.toClipEntry()

                        coroutineScope.launch {
                            clipboard.setClipEntry(clipEntry)
                        }
                    },
                ),
        )
    }
    // todo: consider other types of words?
    // todo: consider "action buttons". For example, "intensify" or "weaken". Also may require custom ListItem type
}
