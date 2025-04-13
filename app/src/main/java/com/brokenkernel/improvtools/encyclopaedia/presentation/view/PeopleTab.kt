package com.brokenkernel.improvtools.encyclopaedia.presentation.view

import android.app.ActivityOptions
import android.content.Intent
import android.content.res.Resources
import android.icu.text.Collator
import android.icu.text.SearchIterator.DONE
import android.icu.text.StringSearch
import android.icu.util.ULocale
import android.os.Bundle
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
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.application.presentation.view.verticalColumnScrollbar
import com.brokenkernel.improvtools.encyclopaedia.data.model.PeopleDaatum
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
                        if (foundText) {
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
                                        IconButton(onClick = {
                                            // webview.setWebViewClient(new WebViewClient());
                                            val browserIntent = Intent(Intent.ACTION_VIEW, it.wikipediaLink);
                                            context.startActivity(browserIntent)
                                        }) {
                                            Icon(
                                                painterResource(R.drawable.logo_wikipedia),
                                                contentDescription = stringResource(R.string.wikipedia),
                                            )
                                        }
                                    }
                                }
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