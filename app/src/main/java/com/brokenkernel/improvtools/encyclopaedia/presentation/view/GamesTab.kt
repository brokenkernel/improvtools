package com.brokenkernel.improvtools.encyclopaedia.presentation.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.Games
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FormatQuote
import androidx.compose.material.icons.outlined.Games
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastAny
import com.brokenkernel.improvtools.application.presentation.view.verticalColumnScrollbar
import com.brokenkernel.improvtools.encyclopaedia.data.model.GamesDatum


private fun String.transformForSearch(): String {
    return this.lowercase().filterNot { it.isWhitespace() }
}

private fun doesMatch(search: String, gameData: GamesDatum): Boolean {
    return gameData.gameName.transformForSearch().contains(search) or
            gameData.topic.transformForSearch().contains(search) or
            gameData.unpublishedMatches.map { it -> it.transformForSearch() }.fastAny {  it -> it.contains(search)}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun GamesTab() {
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
            modifier = Modifier.align(Alignment.TopCenter).semantics { traversalIndex = 0f },
            windowInsets = WindowInsets(top = 0.dp),
        ) {
            Column(
                modifier = Modifier.verticalColumnScrollbar(scrollState)
                    .verticalScroll(scrollState)

            ) {
                GamesDatum.entries.forEach { it: GamesDatum ->
                    if (doesMatch(textFieldState.text.toString().transformForSearch(), it)) {
                        ListItem(
                            headlineContent = { Text(it.gameName) },
                            leadingContent = {
                                Icon(
                                    when (it.topic) {
                                        // TODO: maybe put into `it` or make topic enum?
                                        "Warmup" -> Icons.Filled.Games
                                        "Format" -> Icons.Outlined.FormatQuote
                                        "Game" -> Icons.Outlined.Games
                                        else -> Icons.Outlined.Games
                                    },
                                    contentDescription = "Person",
                                )
                            },
                            overlineContent = { Text(it.topic) }
                        )
                    }
                }
//                ListItem(
//                    headlineContent = { Text("heading") },
//                    supportingContent = { Text("supporting") },
//                    leadingContent = { Text("leading") },
//                    overlineContent = { Text("overline") },
//                    trailingContent = { Text("trailing") },
//                )
            }
        }
    }
}