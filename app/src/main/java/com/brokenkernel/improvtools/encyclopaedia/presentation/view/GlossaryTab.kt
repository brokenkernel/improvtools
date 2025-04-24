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
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.core.net.toUri
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.application.presentation.view.verticalColumnScrollbar
import com.brokenkernel.improvtools.components.presentation.view.HtmlText
import com.brokenkernel.improvtools.components.presentation.view.SimpleSearchBar
import com.brokenkernel.improvtools.components.presentation.view.openInCustomTab
import com.brokenkernel.improvtools.encyclopaedia.data.model.GlossaryDataItem
import com.brokenkernel.improvtools.encyclopaedia.data.model.GlossaryDatum
import com.brokenkernel.improvtools.encyclopaedia.data.model.GlossaryDatumTopic

private fun String.transformForSearch(): String {
    return this.lowercase().filterNot { it.isWhitespace() }
}

private fun doesMatch(search: String, item: GlossaryDataItem): Boolean {
    return item.term.transformForSearch().contains(search)
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
internal fun GlossaryTab(onLaunchTitleCallback: () -> Unit) {
    Column {
        // TODO: consider making a BaseScreenComposable or some such
        LaunchedEffect(Unit) {
            onLaunchTitleCallback()
        }

        val scrollState = rememberScrollState()
        val textFieldState: TextFieldState = rememberTextFieldState()
        val isSegmentedButtonChecked: SnapshotStateList<Boolean> =
            MutableList(GlossaryDatumTopic.entries.size, { true })
                .toMutableStateList()

//        EnumLinkedMultiChoiceSegmentedButtonRow<GlossaryDatumTopic>(
//            isSegmentedButtonChecked = isSegmentedButtonChecked,
//            enumToName = { it -> it.name },
//        )
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
                    GlossaryDatum.sortedBy { it.term }.forEach { it: GlossaryDataItem ->
                        var isListItemInformationExpanded: Boolean by remember {
                            mutableStateOf(
                                false,
                            )
                        }
                        if (isSegmentedButtonChecked[it.topic.ordinal] &&
                            doesMatch(
                                textFieldState.text.toString().transformForSearch(),
                                it,
                            )
                        ) {
                            ListItem(
                                headlineContent = { Text(it.term) },
                                leadingContent = {
                                    Icon(
                                        Icons.Outlined.Backpack,
                                        contentDescription = stringResource(R.string.glossary_term),
                                    )
                                },
                                supportingContent = {
                                    val context = LocalContext.current
                                    if (isListItemInformationExpanded) {
                                        HtmlText(
                                            it.detailedInformation,
                                            onUrlClick = { it ->
                                                openInCustomTab(context, it.toUri())
                                            },
                                        )
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
