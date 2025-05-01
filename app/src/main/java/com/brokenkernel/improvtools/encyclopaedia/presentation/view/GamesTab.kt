package com.brokenkernel.improvtools.encyclopaedia.presentation.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateSetOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.util.fastAny
import com.brokenkernel.components.view.HtmlText
import com.brokenkernel.improvtools.components.presentation.view.ExpandIcon
import com.brokenkernel.improvtools.components.presentation.view.TabbedSearchableColumn
import com.brokenkernel.improvtools.encyclopaedia.data.model.GamesDataItem
import com.brokenkernel.improvtools.encyclopaedia.data.model.GamesDatumTag
import com.brokenkernel.improvtools.encyclopaedia.data.model.GamesDatumTopic
import com.brokenkernel.improvtools.encyclopaedia.presentation.viewmodel.GamesTabViewModel
import kotlinx.coroutines.launch

private fun transformForSearch(str: String): String {
    return str.filterNot { it.isWhitespace() }
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

    val modalBottomSheetState = rememberModalBottomSheetState()
    val textFieldState = rememberTextFieldState()
    var shouldShowTagEnabled by remember { mutableStateOf(false) }
    var shouldShowModalBottomSheet by remember { mutableStateOf(false) }
    val coroutine = rememberCoroutineScope()
    val currentTags = remember { mutableStateSetOf<GamesDatumTag>() }

    fun doesMatch(search: String, gameData: GamesDataItem): Boolean {
        return transformForSearch(gameData.gameName).contains(search, ignoreCase = true) or
            gameData.unpublishedMatches.map { it ->
                transformForSearch(it)
            }
                .fastAny { it -> it.contains(search, ignoreCase = true) } or
            gameData.tags.intersect(currentTags).isNotEmpty()
    }

    LaunchedEffect(textFieldState.text) {
        val text = textFieldState.text
        val asSplit = text.split(" ")
        val onlyTags = asSplit.filter { w ->
            w.isNotEmpty() && w[0] == '#'
        }
        val onlyTagsToTypedTags = onlyTags.mapNotNull { tag ->
            GamesDatumTag.fromLabel(tag)
        }
        currentTags.clear()
        currentTags.addAll(onlyTagsToTypedTags)
        if (onlyTags.isNotEmpty()) {
            shouldShowTagEnabled = true
        } else {
            shouldShowTagEnabled = false
            shouldShowModalBottomSheet = false
        }
    }

    fun doSearchForTag(term: String) {
        textFieldState.setTextAndPlaceCursorAtEnd(term)
    }

    TabbedSearchableColumn<GamesDatumTopic, GamesDataItem>(
        itemDoesMatch = ::doesMatch,
        itemList = viewModel.groupedGames,
        transformForSearch = ::transformForSearch,
        itemToTopic = { it -> it.topic },
        itemToKey = { it -> it.gameName },
        textFieldState = textFieldState,
        trailingIcon = {
            IconButton(
                onClick = {
                    coroutine.launch {
                        shouldShowModalBottomSheet = true
                        modalBottomSheetState.partialExpand()
                    }
                },
                enabled = shouldShowTagEnabled,
            ) {
                Icon(
                    Icons.Default.Tag,
                    contentDescription = "Tags",
                )
            }
        },
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
                Column {
                    if (isListItemInformationExpanded) {
                        if (it.detailedInformation != null) {
                            HtmlText(it.detailedInformation)
                        }
                        if (it.source != null) {
                            HtmlText("""<i><a href="${it.source}">source</a></i>""")
                        }
                    }
                    Row {
                        it.tags.forEach { tag: GamesDatumTag ->
                            TextButton(
                                onClick = {
                                    doSearchForTag(tag.label)
                                },
                            ) {
                                Text(tag.label)
                            }
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

    if (shouldShowModalBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                shouldShowModalBottomSheet = false
            },
            sheetState = modalBottomSheetState,
        ) {
            LazyColumn {
                items(currentTags.toList(), key = { k -> k }) { tag ->
                    Text("Tags")
                    Text(tag.label)
                }
            }
        }
    }
// TODO: make modal bottom sheet a layout
//    // TODO: maybe bottom sheet should be held by scaffold and moved by navigation
//
}
