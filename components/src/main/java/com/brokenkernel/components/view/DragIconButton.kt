package com.brokenkernel.components.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DragHandle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import com.brokenkernel.components.R
import sh.calvin.reorderable.ReorderableCollectionItemScope

@Composable
public fun DragIconButton(
    scope: ReorderableCollectionItemScope,
    modifier: Modifier = Modifier,
) {
    val haptic = LocalHapticFeedback.current
    val longPressModifier: Modifier = with(scope) {
        Modifier.Companion.longPressDraggableHandle(
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
        icon = Icons.Rounded.DragHandle,
        contentDescription = stringResource(R.string.reorder),
    )
}
