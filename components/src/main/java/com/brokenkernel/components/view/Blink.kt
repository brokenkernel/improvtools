package com.brokenkernel.components.view

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@Composable
public fun Blink(
    modifier: Modifier = Modifier,
    duration: Duration = 1.seconds,
    content: @Composable (Modifier) -> Unit,
) {
    val infiniteTransition = rememberInfiniteTransition()

    val alpha: Float by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = duration.inWholeMilliseconds.toInt()),
            repeatMode = RepeatMode.Reverse,
        ),
    )
    content(modifier.alpha(alpha))
}
