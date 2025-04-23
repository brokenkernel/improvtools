package com.brokenkernel.improvtools.encyclopaedia.presentation.view

import android.content.Intent
import android.icu.text.Collator
import android.icu.text.SearchIterator.DONE
import android.icu.text.StringSearch
import android.icu.util.ULocale
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.intl.Locale
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.components.presentation.view.EnumLinkedMultiChoiceSegmentedButtonRow
import com.brokenkernel.improvtools.components.presentation.view.SimpleSearchBar
import com.brokenkernel.improvtools.encyclopaedia.data.model.PeopleDatum
import com.brokenkernel.improvtools.encyclopaedia.data.model.PeopleDatumTopic
import java.text.StringCharacterIterator

private fun String.transformForSearch(): String {
    return this.lowercase().filterNot { it.isWhitespace() }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PeopleTab(onLaunchTitleCallback: () -> Unit) {
    // TODO: consider making a BaseScreenComposable or some such
    LaunchedEffect(Unit) {
        // TODO: maybe SideEffect?
        onLaunchTitleCallback()
    }

    Column {
        val textFieldState = rememberTextFieldState()
        val isSegmentedButtonChecked: SnapshotStateList<Boolean> =
            MutableList(PeopleDatumTopic.entries.size, { true })
                .toMutableStateList()

        EnumLinkedMultiChoiceSegmentedButtonRow<PeopleDatumTopic>(
            isSegmentedButtonChecked = isSegmentedButtonChecked,
            enumToName = { it -> it.name },
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .semantics { isTraversalGroup = true },
        ) {
            SimpleSearchBar(
                textFieldState = textFieldState,
            ) {
                val languageTag = Locale.current.toLanguageTag()
                val fullCollationTag = "$languageTag@collation=phonebook"
                val ulocale = ULocale(fullCollationTag)
                val collator = Collator.getInstance(ulocale)
                val comparator: Comparator<String> = Comparator { s1, s2 ->
                    collator.compare(s1, s2)
                }

                LazyColumn {
                    items(
                        PeopleDatum.sortedWith { s1, s2 ->
                            comparator.compare(
                                s1.personName,
                                s2.personName,
                            )
                        },
                    ) { it ->
                        var isListItemInformationExpanded: Boolean by remember {
                            mutableStateOf(
                                false,
                            )
                        }
                        val foundText =
                            if (textFieldState.text.isNotEmpty()) {
                                // there is probably a better way to handle transformForSearch
                                // by directly asking ICU to convert to lowercase and so on, but keep for now while I figure this out.
                                val search = StringSearch(
                                    textFieldState.text.toString().transformForSearch(),
                                    StringCharacterIterator(it.personName.transformForSearch()),
                                    Locale.current.platformLocale,
                                )
                                search.first() != DONE
                            } else {
                                true
                            }
                        if (isSegmentedButtonChecked[it.topic.ordinal] && foundText) {
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
                                                val browserIntent =
                                                    Intent(Intent.ACTION_VIEW, it.wikipediaLink)
                                                context.startActivity(browserIntent)
                                            },
                                        ) {
                                            Icon(
                                                painterResource(R.drawable.logo_wikipedia),
                                                contentDescription = stringResource(
                                                    R.string.wikipedia,
                                                ),
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
                                ),
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
