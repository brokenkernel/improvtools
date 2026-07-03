package com.brokenkernel.components.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.brokenkernel.components.R
import sh.calvin.reorderable.ReorderableCollectionItemScope

@Composable
public fun DragIconButton(
    scope: ReorderableCollectionItemScope,
    modifier: Modifier = Modifier,
) {
    val haptic = LocalHapticFeedback.current
    val longPressModifier: Modifier = with(scope) {
        Modifier.longPressDraggableHandle(
            onDragStarted = {
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            },
            onDragStopped = {
                haptic.performHapticFeedback(HapticFeedbackType.GestureEnd)
            },
        )
    }

    SimpleIconButton(
        modifier = modifier.then(longPressModifier),
        onClick = {},
        icon = ImageVector.vectorResource(R.drawable.drag_handle_24dp_1f1f1f_fill0_wght400_grad0_opsz24),
        contentDescription = stringResource(R.string.reorder),
    )
}
