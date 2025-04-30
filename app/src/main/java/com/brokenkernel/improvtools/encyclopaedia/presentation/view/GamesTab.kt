package com.brokenkernel.improvtools.encyclopaedia.presentation.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.util.fastAny
import com.brokenkernel.improvtools.components.presentation.view.ExpandIcon
import com.brokenkernel.improvtools.components.presentation.view.HtmlText
import com.brokenkernel.improvtools.components.presentation.view.TabbedSearchableColumn
import com.brokenkernel.improvtools.encyclopaedia.data.model.GamesDataItem
import com.brokenkernel.improvtools.encyclopaedia.data.model.GamesDatumTopic
import com.brokenkernel.improvtools.encyclopaedia.presentation.viewmodel.GamesTabViewModel

private fun transformForSearch(str: String): String {
    return str.filterNot { it.isWhitespace() }
}

private fun doesMatch(search: String, gameData: GamesDataItem): Boolean {
    return transformForSearch(gameData.gameName).contains(search, ignoreCase = true) or
        gameData.unpublishedMatches.map { it ->
            transformForSearch(it)
        }
            .fastAny { it -> it.contains(search, ignoreCase = true) }
}

private fun hasExpandableInformation(gdi: GamesDataItem): Boolean {
    return gdi.detailedInformation != null || gdi.source != null
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
internal fun GamesTab(
    onLaunchTitleCallback: () -> Unit,
    viewModel: GamesTabViewModel = GamesTabViewModel(),
) {
    // TODO: consider making a BaseScreenComposable or some such
    LaunchedEffect(Unit) {
        onLaunchTitleCallback()
    }

    val sortedGames = remember { viewModel.sortedGames }

    TabbedSearchableColumn<GamesDatumTopic, GamesDataItem>(
        itemDoesMatch = ::doesMatch,
        itemList = sortedGames,
        transformForSearch = ::transformForSearch,
        itemToTopic = { it -> it.topic },
        itemToKey = { it -> it.gameName },
    ) { it: GamesDataItem ->
        var isListItemInformationExpanded: Boolean by remember {
            mutableStateOf(
                false,
            )
        }
        val gdiModifier =
            if (hasExpandableInformation(it)) {
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
            headlineContent = { Text(it.gameName) },
            leadingContent = {
                Icon(
                    it.topic.icon,
                    contentDescription = it.topic.toString(),
                )
            },
            overlineContent = { Text(it.topic.name) },
            supportingContent = {
                if (isListItemInformationExpanded) {
                    Column {
                        if (it.detailedInformation != null) {
                            HtmlText(it.detailedInformation)
                        }
                        if (it.source != null) {
                            HtmlText("""<i><a href="${it.source}">source</a></i>""")
                        }
                    }
                }
            },
            trailingContent = {
                if (hasExpandableInformation(it)) {
                    ExpandIcon(isListItemInformationExpanded)
                }
            },
            modifier = gdiModifier,
        )
    }
}
