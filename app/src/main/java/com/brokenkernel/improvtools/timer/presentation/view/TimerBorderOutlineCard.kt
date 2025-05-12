package com.brokenkernel.improvtools.timer.presentation.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
internal fun TimerBorderOutlineCard(
    modifier: Modifier = Modifier,
    content: @Composable (() -> Unit) = {},
) {
    OutlinedCard(
        modifier = modifier.fillMaxWidth(),
        border = BorderStroke(1.dp, Color.Black),
    ) {
        content()
    }
}
