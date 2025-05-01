package com.brokenkernel.improvtools.components.presentation.view

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
internal fun OneWayDismissableContent(
    onRemove: () -> Unit,
    content: @Composable (() -> Unit) = {},
) {
    // TODO: https://archive.is/I4TPn
//    val currentItem by rememberUpdatedState(item)

    val contentSTDState = rememberSwipeToDismissBoxState(
        confirmValueChange = { newValue ->
            if (newValue == SwipeToDismissBoxValue.EndToStart) {
                onRemove()
                true
            } else {
                false
            }
        },
    )

    SwipeToDismissBox(
        state = contentSTDState,
        backgroundContent = {
            val color by
                animateColorAsState(
                    when (contentSTDState.dismissDirection) {
                        // TODO: from theme?
                        SwipeToDismissBoxValue.Settled -> Color.LightGray
                        SwipeToDismissBoxValue.StartToEnd -> Color.Red
                        SwipeToDismissBoxValue.EndToStart -> Color.Red
                    },
                )
            Box(
                Modifier
                    .fillMaxSize()
                    .background(color),
            )
        },
        enableDismissFromStartToEnd = false,
        enableDismissFromEndToStart = true,
        gesturesEnabled = true,
    ) {
        content()
    }
}
