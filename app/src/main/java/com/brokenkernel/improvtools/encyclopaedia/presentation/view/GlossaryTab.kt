package com.brokenkernel.improvtools.encyclopaedia.presentation.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Backpack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import com.brokenkernel.components.view.HtmlText
import com.brokenkernel.components.view.TabbedSearchableColumn
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.components.presentation.view.ExpandIcon
import com.brokenkernel.improvtools.encyclopaedia.data.model.GlossaryDataItem
import com.brokenkernel.improvtools.encyclopaedia.data.model.GlossaryDatum
import com.brokenkernel.improvtools.encyclopaedia.data.model.GlossaryDatumTopic

private fun transformForSearch(str: String): String {
    return str.filterNot { it.isWhitespace() }
}

private fun doesMatch(search: String, item: GlossaryDataItem): Boolean {
    return transformForSearch(item.term).contains(search, ignoreCase = true)
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
internal fun GlossaryTab(onLaunchTitleCallback: () -> Unit) {
    // TODO: consider making a BaseScreenComposable or some such
    LaunchedEffect(Unit) {
        onLaunchTitleCallback()
    }

    // TODO: move into viewModel
    val sortedGlossaryItems: Map<String, List<GlossaryDataItem>> = remember {
        GlossaryDatum.sortedBy { it.term }.groupBy { it.term[0].uppercase() }
    }
    TabbedSearchableColumn<GlossaryDatumTopic, GlossaryDataItem>(
        itemDoesMatch = ::doesMatch,
        itemList = sortedGlossaryItems,
        transformForSearch = ::transformForSearch,
        itemToTopic = { it -> it.topic },
        itemToKey = { it -> it.term },
    ) { it: GlossaryDataItem ->
        var isListItemInformationExpanded: Boolean by remember {
            mutableStateOf(
                false,
            )
        }
        ListItem(
            headlineContent = { Text(it.term) },
            leadingContent = {
                Icon(
                    Icons.Outlined.Backpack,
                    contentDescription = stringResource(R.string.glossary_term),
                )
            },
            supportingContent = {
                if (isListItemInformationExpanded) {
                    HtmlText(it.detailedInformation)
                }
            },
            trailingContent = {
                ExpandIcon(isExpanded = isListItemInformationExpanded)
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
