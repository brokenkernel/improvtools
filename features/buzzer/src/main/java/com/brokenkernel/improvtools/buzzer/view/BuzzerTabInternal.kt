package com.brokenkernel.improvtools.buzzer.view

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.ToneGenerator
import androidx.annotation.RawRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.brokenkernel.improvtools.buzzer.R

// bell beep buzz
@Composable
private fun BuzzerButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit,
) {
    ElevatedButton(
        onClick = onClick,
        shape = CardDefaults.elevatedShape,
        modifier = Modifier
            .size(width = 240.dp, height = 100.dp),
        enabled = enabled,
        content = content,
    )
}

private class Beeper(private val context: Context) {
    // TODO: figure out how to make MediaPlayer allow for dynamic option.
    // TODO: prepare async. LoadableScreenn?
    private val mediaPlayer = MediaPlayer.create(
        context,
        R.raw.freesound_community_gong_92707,
    ).apply {
//        setDataSource()
        setAudioAttributes(
            AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build(),
        )
    }

    fun play(@RawRes id: Int) {
        context.resources.openRawResource(id)
        mediaPlayer.start()
    }
}

@Composable
public fun BuzzerTabInternal(modifier: Modifier = Modifier) {
    val beeper = Beeper(LocalContext.current)
    val toneGenerator = ToneGenerator(AudioManager.STREAM_MUSIC, 100)

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
    ) {
        // TODO: figure out how to make this into a model instead of fixed in the UI
        item {
            BuzzerButton(
                onClick = {
                    toneGenerator.startTone(ToneGenerator.TONE_CDMA_PIP, 150)
                },
                enabled = false,
            ) {
                Text(stringResource(R.string.tone_bell))
            }
        }
        item {
            BuzzerButton(
                onClick = {
                    // TODO: pick a good tone?
                    toneGenerator.startTone(ToneGenerator.TONE_CDMA_PIP, 350)
                },
            ) {
                Text(stringResource(R.string.tone_beep))
            }
        }
        item {
            BuzzerButton(
                onClick = {
                    toneGenerator.startTone(ToneGenerator.TONE_CDMA_PIP, 150)
                },
                enabled = false,
            ) {
                Text(stringResource(R.string.tone_buzz))
            }
        }
        item {
            BuzzerButton(
                onClick = {
                    beeper.play(R.raw.freesound_community_gong_92707)
                },
            ) {
                Text(stringResource(R.string.tone_gong))
            }
        }
    }
}
