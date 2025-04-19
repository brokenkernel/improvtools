package com.brokenkernel.improvtools.encyclopaedia.presentation.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.util.fastAny
import com.brokenkernel.improvtools.application.presentation.view.verticalColumnScrollbar
import com.brokenkernel.improvtools.components.presentation.view.EnumLinkedMultiChoiceSegmentedButtonRow
import com.brokenkernel.improvtools.components.presentation.view.SimpleSearchBar
import com.brokenkernel.improvtools.encyclopaedia.data.model.GamesDataItem
import com.brokenkernel.improvtools.encyclopaedia.data.model.GamesDatum
import com.brokenkernel.improvtools.encyclopaedia.data.model.GamesDatumTopic

private fun String.transformForSearch(): String {
    return this.lowercase().filterNot { it.isWhitespace() }
}

private fun doesMatch(search: String, gameData: GamesDataItem): Boolean {
    return gameData.gameName.transformForSearch().contains(search) or
        gameData.unpublishedMatches.map { it -> it.transformForSearch() }.fastAny { it -> it.contains(search) }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
internal fun GamesTab() {
    Column {
        val scrollState = rememberScrollState()
        val textFieldState: TextFieldState = rememberTextFieldState()
        val isSegmentedButtonChecked: SnapshotStateList<Boolean> =
            MutableList(GamesDatumTopic.entries.size, { true })
                .toMutableStateList()

        EnumLinkedMultiChoiceSegmentedButtonRow<GamesDatumTopic>(
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
                Column(
                    modifier = Modifier
                        .verticalColumnScrollbar(scrollState)
                        .verticalScroll(scrollState),

                ) {
                    GamesDatum.sortedBy { it.gameName }.forEach { it: GamesDataItem ->
                        var isListItemInformationExpanded: Boolean by remember { mutableStateOf(false) }
                        if (isSegmentedButtonChecked[it.topic.ordinal] && doesMatch(
                                textFieldState.text.toString().transformForSearch(), it,
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
                                            GamesDatumTopic.EXERCISE -> Icons.Outlined.SelfImprovement
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
                                ),
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
