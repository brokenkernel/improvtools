package com.brokenkernel.improvtools.encyclopaedia.presentation.view

import android.content.Intent
import android.icu.text.Collator
import android.icu.text.SearchIterator.DONE
import android.icu.text.StringSearch
import android.icu.util.ULocale
import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.application.presentation.view.verticalColumnScrollbar
import com.brokenkernel.improvtools.encyclopaedia.data.model.PeopleDaatum
import com.brokenkernel.improvtools.encyclopaedia.data.model.PeopleDatumTopic
import java.text.StringCharacterIterator


private fun String.transformForSearch(): String {
    return this.lowercase().filterNot { it.isWhitespace() }
}

// TODO: sort, search, filter by name
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PeopleTab() {
    var searchBarExpandedState by rememberSaveable { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    val textFieldState = rememberTextFieldState()
    Column {
        // TODO: figure out how to deal with dynamic size and derive from size of enum
        val isSegementedButtonChecked: SnapshotStateList<Boolean> = remember { mutableStateListOf(true, true) }

        MultiChoiceSegmentedButtonRow {
            // TODO: i18n
            PeopleDatumTopic.entries.forEach { topic ->
                SegmentedButton(
                    onCheckedChange = {
                        isSegementedButtonChecked[topic.ordinal] = !isSegementedButtonChecked[topic.ordinal]
                    },
                    checked = isSegementedButtonChecked[topic.ordinal],
                    shape = SegmentedButtonDefaults.itemShape(
                        index = topic.ordinal,
                        count = PeopleDatumTopic.entries.size,
                    ),
                    label = { Text(topic.name) },
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .semantics { isTraversalGroup = true }) {
            SearchBar(
                inputField = {
                    SearchBarDefaults.InputField(
                        query = textFieldState.text.toString(),
                        onQueryChange = { it ->
                            textFieldState.edit { replace(0, length, it) }
                        },
                        onSearch = {
                            searchBarExpandedState = false
                        },
                        expanded = searchBarExpandedState,
                        onExpandedChange = { searchBarExpandedState != searchBarExpandedState },
                        placeholder = { Text("Search") },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Search,
                                contentDescription = "Search"
                            )
                        },
//                    trailingIcon = {
//                        Icon(
//                            Icons.Default.UnfoldMoreDouble,
//                            contentDescription = "Search"
//                        )
//                    },
                    )
                },
                expanded = true,
                onExpandedChange = { it -> searchBarExpandedState = it },
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .semantics { traversalIndex = 0f },
                windowInsets = WindowInsets(top = 0.dp),
            ) {
                val languageTag = Locale.current.toLanguageTag()
                val fullCollationTag = "$languageTag@collation=phonebook"
                val ulocale = ULocale(fullCollationTag)
                val collator = Collator.getInstance(ulocale)
                val comparator: Comparator<String> = Comparator { s1, s2 ->
                    collator.compare(s1, s2)
                }

                Column(
                    modifier = Modifier
                        .verticalColumnScrollbar(scrollState)
                        .verticalScroll(scrollState)

                ) {
                    //
                    PeopleDaatum.entries.sortedWith { s1, s2 -> comparator.compare(s1.personName, s2.personName) }
                        .forEach { it ->
                            var isListItemInformationExpanded: Boolean by remember { mutableStateOf(false) }
                            val foundText =
                                if (textFieldState.text.isNotEmpty()) {
                                    // there is probably a better way to handle transformForSearch
                                    // by directly asking ICU to convert to lowercase and so on, but keep for now while I figure this out.
                                    val search = StringSearch(
                                        textFieldState.text.toString().transformForSearch(),
                                        StringCharacterIterator(it.personName.transformForSearch()),
                                        Locale.current.platformLocale
                                    )
                                    search.first() != DONE
                                } else {
                                    true
                                }
                            if (isSegementedButtonChecked[it.topic.ordinal] && foundText) {
                                ListItem(
                                    headlineContent = { Text(it.personName) },
                                    leadingContent = {
                                        Icon(
                                            Icons.Outlined.Person,
                                            contentDescription = "Person",
                                        )
                                    },
                                    overlineContent = { Text(it.knownFor) },
                                    trailingContent = {
                                        if (it.wikipediaLink != null) {
                                            val context = LocalContext.current
                                            OutlinedIconButton(
                                                onClick = {
                                                    // webview.setWebViewClient(new WebViewClient());
                                                    val browserIntent = Intent(Intent.ACTION_VIEW, it.wikipediaLink);
                                                    context.startActivity(browserIntent)
                                                },
                                            ) {
                                                Icon(
                                                    painterResource(R.drawable.logo_wikipedia),
                                                    contentDescription = stringResource(R.string.wikipedia),
                                                )
                                            }
                                        }
                                    },
                                    supportingContent = {
                                        if (isListItemInformationExpanded) {
                                            Text(it.detailedInformation)
                                        }
                                    },
                                    modifier = Modifier.clickable(
                                        enabled = true,
                                        role = Role.Button,
                                        onClick = {
                                            isListItemInformationExpanded = !isListItemInformationExpanded
                                        },
                                    )
                                )
                            }
                        }
//                ListItem(
//                    headlineContent = { Text("headline") },
//                    leadingContent = { Text("leading") },
//                    trailingContent = { Text("trailing") },
//                    supportingContent = { Text("supporting") },
//                    overlineContent = { Text("overline") },
//                )
                }
            }
        }
    }
}