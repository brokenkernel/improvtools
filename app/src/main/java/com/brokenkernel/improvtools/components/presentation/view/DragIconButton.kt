package com.brokenkernel.improvtools.components.presentation.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DragHandle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import com.brokenkernel.components.view.SimpleIconButton
import com.brokenkernel.improvtools.R
import sh.calvin.reorderable.ReorderableCollectionItemScope

@Composable
fun DragIconButton(
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
        icon = Icons.Rounded.DragHandle,
        contentDescription = stringResource(R.string.reorder),
    )
}
