package com.brokenkernel.improvtools.suggestionGenerator.presentation

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float,
    style: TextStyle,
) {
    Text(
        text = text,
        modifier = Modifier
            .border(1.dp, MaterialTheme.colorScheme.tertiary)
            .fillMaxHeight()
            .weight(weight)
            .padding(8.dp)
            .fillMaxWidth(),
        style = style,
    )
}

@Composable
fun RowScope.ClickableTableCell(
    text: String,
    weight: Float,
    style: TextStyle,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        modifier = Modifier
            .border(1.dp, MaterialTheme.colorScheme.tertiary)
            .weight(weight)
            .fillMaxWidth()
            .fillMaxHeight()
    )
    {
        Text(
            text = text,
            style = style,
        )
    }
}