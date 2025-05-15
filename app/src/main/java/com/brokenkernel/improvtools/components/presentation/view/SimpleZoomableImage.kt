package com.brokenkernel.improvtools.components.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.zoomable

@Composable
internal fun SimpleZoomableImage(
    painter: Painter,
    contentDescription: String,
    modifier: Modifier = Modifier,
) {
    val painterZoomState = rememberZoomState(contentSize = painter.intrinsicSize)

    Image(
        painter = painter,
        contentDescription = contentDescription,
        contentScale = ContentScale.Fit,
        modifier = modifier
            .zoomable(painterZoomState),
    )
}
