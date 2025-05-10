package com.brokenkernel.improvtools.encyclopaedia.presentation.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.brokenkernel.components.view.HtmlText
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.application.navigation.ImprovToolsDestination
import com.brokenkernel.improvtools.application.presentation.view.verticalColumnScrollbar
import com.brokenkernel.improvtools.components.presentation.view.SimpleZoomableImage
import com.brokenkernel.improvtools.components.sidecar.navigation.ImprovToolsNavigationGraph
import com.brokenkernel.improvtools.components.sidecar.preview.ImprovToolsAllPreviews

@ImprovToolsDestination<ImprovToolsNavigationGraph>
@Composable
internal fun EmotionTab() {
    val columnScrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalColumnScrollbar(columnScrollState)
            .verticalScroll(columnScrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        OutlinedCard(
            modifier = Modifier.fillMaxWidth(),
            border = BorderStroke(1.dp, Color.Black),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
        ) {
            Text(
                stringResource(R.string.encyclopaedia_plutchiks_wheel_of_emotions),
                style = MaterialTheme.typography.titleLarge,
            )
            SimpleZoomableImage(
                painter = painterResource(R.drawable.plutchik_wheel),
                contentDescription = stringResource(
                    R.string.encyclopaedia_plutchiks_wheel_of_emotions,
                ),
            )
        }
        OutlinedCard {
            Text(
                stringResource(R.string.encyclopaedia_emotion_wheel),
                style = MaterialTheme.typography.titleLarge,
            )
            SimpleZoomableImage(
                painter = painterResource(R.drawable.general_emotion_wheel),
                contentDescription = stringResource(
                    R.string.encyclopaedia_plutchiks_wheel_of_emotions,
                ),
            )
        }
        // TODO: consider pulling these out into data class, and exposing in Thesaurus.
        OutlinedCard(
            modifier = Modifier.fillMaxWidth(),
            border = BorderStroke(1.dp, Color.Black),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
        ) {
            Text(
                stringResource(R.string.encyclopaedia_emotion_words),
                style = MaterialTheme.typography.titleLarge,
            )
            HtmlText(
                """
                    |<ul>
                    |<li>
                    |<h2>Nurturing</h2>
                    |<ul>
                    |<li>Loving</li>
                    |<li>Encouraging</li>
                    |<li>Supporting</li>
                    |</ul>
                    |</li>
                    |
                    |<li>
                    |<h2>Using</h2>
                    |<ul>
                    |<li>Manipulating</li>
                    |<li>Distributing</li>
                    |<li>Deceiving</li>
                    |</ul>
                    |</li>
                    |
                    |<li>
                    |<h2>Damaging</h2>
                    |<ul>
                    |<li>Discouraging</li>
                    |<li>Harming</li>
                    |<li>Destroying</li>
                    |</ul>
                    |</ul>
                    |</li>
                    |
                """.trimMargin(),
            )
        }
    }
}

@ImprovToolsAllPreviews
@Composable
internal fun EmotionTabPreview() {
    EmotionTab()
}
