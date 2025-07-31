package com.brokenkernel.components.view

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults.rememberTooltipPositionProvider
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
public fun SimpleTooltipWrapper(
    tooltip: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    TooltipBox(
        positionProvider = rememberTooltipPositionProvider(TooltipAnchorPosition.Above),
        state = rememberTooltipState(),
        tooltip = {
            PlainTooltip {
                Text(
                    tooltip,
                )
            }
        },
        modifier = modifier,
        content = content,
    )
}
