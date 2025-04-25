package com.brokenkernel.improvtools.encyclopaedia.presentation.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Games
import androidx.compose.material.icons.outlined.FormatQuote
import androidx.compose.material.icons.outlined.Games
import androidx.compose.material.icons.outlined.SelfImprovement
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
import com.brokenkernel.improvtools.components.presentation.view.HtmlText
import com.brokenkernel.improvtools.components.presentation.view.TabbedSearchableColumn
import com.brokenkernel.improvtools.encyclopaedia.data.model.GamesDataItem
import com.brokenkernel.improvtools.encyclopaedia.data.model.GamesDatum
import com.brokenkernel.improvtools.encyclopaedia.data.model.GamesDatumTopic

private fun transformForSearch(str: String): String {
    return str.lowercase().filterNot { it.isWhitespace() }
}

private fun doesMatch(search: String, gameData: GamesDataItem): Boolean {
    return transformForSearch(gameData.gameName).contains(search) or
        gameData.unpublishedMatches.map { it -> transformForSearch(it) }.fastAny { it -> it.contains(search) }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
internal fun GamesTab(onLaunchTitleCallback: () -> Unit) {
    // TODO: consider making a BaseScreenComposable or some such
    LaunchedEffect(Unit) {
        onLaunchTitleCallback()
    }

    val sortedGames = remember {
        GamesDatum.sortedBy { it.gameName }.toList()
    }

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
        ListItem(
            headlineContent = { Text(it.gameName) },
            leadingContent = {
                Icon(
                    when (it.topic) {
                        GamesDatumTopic.GAME -> Icons.Filled.Games
                        GamesDatumTopic.WARMUP -> Icons.Outlined.Games
                        GamesDatumTopic.FORMAT -> Icons.Outlined.FormatQuote
                        GamesDatumTopic.EXERCISE -> Icons.Outlined.SelfImprovement
                    },
                    contentDescription = "Person", // TODO text
                )
            },
            overlineContent = { Text(it.topic.name) },
            supportingContent = {
                if (isListItemInformationExpanded) {
                    HtmlText(it.detailedInformation)
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
