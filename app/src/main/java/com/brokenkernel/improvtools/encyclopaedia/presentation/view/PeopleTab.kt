package com.brokenkernel.improvtools.encyclopaedia.presentation.view

import android.icu.text.Collator
import android.icu.text.SearchIterator.DONE
import android.icu.text.StringSearch
import android.icu.util.ULocale
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Web
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.intl.Locale
import com.brokenkernel.components.view.TabbedSearchableColumn
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.components.presentation.view.ExpandIcon
import com.brokenkernel.improvtools.encyclopaedia.data.model.PeopleDataItem
import com.brokenkernel.improvtools.encyclopaedia.data.model.PeopleDatum
import com.brokenkernel.improvtools.encyclopaedia.data.model.PeopleDatumTopic
import java.text.StringCharacterIterator
import kotlinx.collections.immutable.toImmutableMap

private fun transformForSearch(str: String): String {
    return str.lowercase().filterNot { it.isWhitespace() }
}

private fun doesMatch(searchString: String, personData: PeopleDataItem): Boolean {
    if (searchString.isNotEmpty()) {
        // there is probably a better way to handle transformForSearch
        // by directly asking ICU to convert to lowercase and so on, but keep for now while I figure this out.
        val search = StringSearch(
            transformForSearch(searchString),
            StringCharacterIterator(transformForSearch(personData.personName)),
            Locale.current.platformLocale,
        )
        return search.first() != DONE
    } else {
        return true
    }
}

private fun hasExpandableInformation(pdi: PeopleDataItem): Boolean {
    return pdi.detailedInformation != null
}

@Composable
internal fun PeopleTab() {
    val languageTag = Locale.current.toLanguageTag()
    val fullCollationTag = "$languageTag@collation=phonebook"
    val ulocale = ULocale(fullCollationTag)
    val collator = Collator.getInstance(ulocale)
    val comparator: Comparator<String> = Comparator { s1, s2 ->
        collator.compare(s1, s2)
    }
    // TODO: consider moving this into the viewModel
    val sortedPeople = remember {
        PeopleDatum.sortedWith { s1, s2 ->
            comparator.compare(
                s1.personName,
                s2.personName,
            )
        }.groupBy { it.personName[0].uppercase() }.toImmutableMap()
    }

    TabbedSearchableColumn<PeopleDatumTopic, PeopleDataItem>(
        itemDoesMatch = ::doesMatch,
        itemList = sortedPeople,
        transformForSearch = ::transformForSearch,
        itemToTopic = { it -> it.topic },
        itemToKey = { it -> it.personName },
    ) { it ->
        var isListItemInformationExpanded: Boolean by remember {
            mutableStateOf(
                false,
            )
        }

        val pdiModifier = if (hasExpandableInformation(it)) {
            Modifier.clickable(
                enabled = true,
                role = Role.Button,
                onClick = {
                    isListItemInformationExpanded = !isListItemInformationExpanded
                },
            )
        } else {
            Modifier
        }

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
                Row {
                    if (it.learnMoreLink != null) {
                        val uriHandler = LocalUriHandler.current
                        OutlinedIconButton(
                            onClick = {
                                uriHandler.openUri(it.learnMoreLink.toString())
                            },
                        ) {
                            if (it.isWikipedia) {
                                Icon(
                                    painterResource(R.drawable.logo_wikipedia),
                                    contentDescription = stringResource(
                                        R.string.wikipedia,
                                    ),
                                )
                            } else {
                                Icon(
                                    Icons.Default.Web,
                                    contentDescription = stringResource(R.string.open_website),
                                )
                            }
                        }
                    }
                    if (hasExpandableInformation(it)) {
                        ExpandIcon(isExpanded = isListItemInformationExpanded)
                    }
                }
            },
            supportingContent = {
                if (isListItemInformationExpanded) {
                    if (it.detailedInformation != null) {
                        Text(it.detailedInformation)
                    }
                }
            },
            modifier = pdiModifier,
        )
    }
}
