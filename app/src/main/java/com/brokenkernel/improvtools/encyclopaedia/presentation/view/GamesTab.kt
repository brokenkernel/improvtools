package com.brokenkernel.improvtools.encyclopaedia.presentation.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateSetOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.util.fastAny
import com.brokenkernel.components.view.HtmlText
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.application.presentation.api.LocalBottomSheetContentManager
import com.brokenkernel.improvtools.components.presentation.view.ExpandIcon
import com.brokenkernel.improvtools.components.presentation.view.TabbedSearchableColumn
import com.brokenkernel.improvtools.encyclopaedia.data.model.GamesDataItem
import com.brokenkernel.improvtools.encyclopaedia.data.model.GamesDatumTag
import com.brokenkernel.improvtools.encyclopaedia.data.model.GamesDatumTopic
import com.brokenkernel.improvtools.encyclopaedia.presentation.viewmodel.GamesTabViewModel

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

    val textFieldState = rememberTextFieldState()
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
    }

    val bottomSheetManager = LocalBottomSheetContentManager.current

    // TODO: this should really be passed in as a navigable function
    fun onNavigateToBottomTag(tag: GamesDatumTag) {
        // TODO: this should really be a destination, but deal with it later
        bottomSheetManager({ SingleTagBottomTab(tag) })
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
                onClick = {},
            ) {
                Icon(
                    Icons.Default.Tag,
                    contentDescription = stringResource(R.string.encyclopaedia_tags_filter_button),
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
                                    onNavigateToBottomTag(tag)
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
}
