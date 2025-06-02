package com.brokenkernel.improvtools.encyclopaedia.android.emotions

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
import com.brokenkernel.components.view.SimpleZoomableImage
import com.brokenkernel.components.view.verticalColumnScrollbar
import com.brokenkernel.improvtools.encyclopaedia.android.R

@Composable
public fun EmotionsTabInternal(
    modifier: Modifier = Modifier,
) {
    val columnScrollState = rememberScrollState()

    Column(
        modifier = modifier
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
                stringResource(R.string.plutchiks_wheel_of_emotions),
                style = MaterialTheme.typography.titleLarge,
            )
            SimpleZoomableImage(
                painter = painterResource(R.drawable.plutchik_wheel),
                contentDescription = stringResource(R.string.plutchiks_wheel_of_emotions),
            )
        }
        OutlinedCard(
            modifier = Modifier.fillMaxWidth(),
            border = BorderStroke(1.dp, Color.Black),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
        ) {
            Text(
                stringResource(R.string.emotion_wheel),
                style = MaterialTheme.typography.titleLarge,
            )
            SimpleZoomableImage(
                painter = painterResource(R.drawable.general_emotion_wheel),
                contentDescription = stringResource(R.string.emotion_wheel),
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
                stringResource(R.string.emotion_words),
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
                    |<li>
                    |<h2>Using</h2>
                    |<ul>
                    |<li>Manipulating</li>
                    |<li>Distributing</li>
                    |<li>Deceiving</li>
                    |</ul>
                    |</li>
                    |<li>
                    |<h2>Damaging</h2>
                    |<ul>
                    |<li>Discouraging</li>
                    |<li>Harming</li>
                    |<li>Destroying</li>
                    |</ul>
                    |</li>
                    |</ul>
                """.trimMargin(),
            )
        }
    }
}
