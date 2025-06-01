package com.brokenkernel.improvtools.encyclopaedia.android.tipsandadvice.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.brokenkernel.components.view.ExpandIcon
import com.brokenkernel.components.view.HtmlText
import com.brokenkernel.improvtools.encyclopaedia.data.tipsandadvice.TipContentUI
import com.brokenkernel.improvtools.encyclopaedia.data.tipsandadvice.TipsAndAdviceUIState

// TODO: make internal
@Composable
public fun TipsAndAdviceScreenAsList(
    uiState: TipsAndAdviceUIState,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(uiState.tipsAndAdvice) { it: TipContentUI ->
            var isExpanded by rememberSaveable {
                mutableStateOf(
                    false,
                )
            }
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                ),
                onClick = {
                    isExpanded = !isExpanded
                },
            ) {
                ListItem(
                    headlineContent = {
                        Text(
                            text = it.title,
                        )
                    },
                    supportingContent = {
                        AnimatedVisibility(isExpanded) {
                            SelectionContainer {
                                HtmlText(it.content)
                            }
                        }
                    },
                    trailingContent = { ExpandIcon(isExpanded) },
                    colors = ListItemDefaults.colors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    ),
                )
            }
        }
    }
}
