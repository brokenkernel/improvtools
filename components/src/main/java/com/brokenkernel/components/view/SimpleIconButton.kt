package com.brokenkernel.components.view

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun SimpleIconButton(
    onClick: () -> Unit,
    icon: ImageVector,
    contentDescription: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Box(modifier) {
        SimpleTooltipWrapper(
            tooltip = contentDescription,
        ) {
            IconButton(
                onClick = onClick,
                enabled = enabled,
            ) {
                Icon(
                    icon,
                    contentDescription = contentDescription,
                )
            }
        }
    }
}
