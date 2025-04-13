package com.brokenkernel.improvtools.encyclopaedia.presentation.view

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Games
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FormatQuote
import androidx.compose.material.icons.outlined.Games
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastAny
import com.brokenkernel.improvtools.application.presentation.view.verticalColumnScrollbar
import com.brokenkernel.improvtools.encyclopaedia.data.model.GamesDatum
import com.brokenkernel.improvtools.encyclopaedia.data.model.GamesDatumTopic


private fun String.transformForSearch(): String {
    return this.lowercase().filterNot { it.isWhitespace() }
}

private fun doesMatch(search: String, gameData: GamesDatum): Boolean {
    return gameData.gameName.transformForSearch().contains(search) or
            gameData.unpublishedMatches.map { it -> it.transformForSearch() }.fastAny { it -> it.contains(search) }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
internal fun GamesTab() {

    var searchBarExpandedState by rememberSaveable { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    val textFieldState = rememberTextFieldState()
    Column {
        // TODO: figure out how to deal with dynamic size and derive from size of enum
        val isSegementedButtonChecked: SnapshotStateList<Boolean> = remember { mutableStateListOf(true, true, true) }

        MultiChoiceSegmentedButtonRow {
            // TODO: i18n
            GamesDatumTopic.entries.forEach { topic ->
                SegmentedButton(
                    onCheckedChange = {
                        isSegementedButtonChecked[topic.ordinal] = !isSegementedButtonChecked[topic.ordinal]
                    },
                    checked = isSegementedButtonChecked[topic.ordinal],
                    shape = SegmentedButtonDefaults.itemShape(
                        index = topic.ordinal,
                        count = GamesDatumTopic.entries.size,
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
                Column(
                    modifier = Modifier
                        .verticalColumnScrollbar(scrollState)
                        .verticalScroll(scrollState)

                ) {
                    GamesDatum.entries.sortedBy { it.gameName }.forEach { it: GamesDatum ->
                        var isListItemInformationExpanded: Boolean by remember { mutableStateOf(false) }
                        if (isSegementedButtonChecked[it.topic.ordinal] && doesMatch(
                                textFieldState.text.toString().transformForSearch(), it
                            )
                        ) {
                            ListItem(
                                headlineContent = { Text(it.gameName) },
                                leadingContent = {
                                    Icon(
                                        when (it.topic) {
                                            GamesDatumTopic.GAME -> Icons.Filled.Games
                                            GamesDatumTopic.WARMUP -> Icons.Outlined.Games
                                            GamesDatumTopic.FORMAT -> Icons.Outlined.FormatQuote
                                        },
                                        contentDescription = "Person", // TODO text
                                    )
                                },
                                overlineContent = { Text(it.topic.name) },
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
}