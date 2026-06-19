package com.brokenkernel.improvtools.buzzer.view

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.brokenkernel.improvtools.buzzer.model.BuzzerDatum

@Composable
public fun BuzzerTabInternal(modifier: Modifier = Modifier) {
    val simpleBuzzerMediaPlayer = SimpleBuzzerMediaPlayer(LocalContext.current)
    // consider changing MediaPlayer API usage to allow restarts, but this avoids crashes for now.
    // TODO: consider shortening the media files :)
    val isPlaying = remember { mutableStateOf(false) }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
    ) {
        items(BuzzerDatum.buzzers) { buz ->
            BuzzerButton(
                onClick = {
                    simpleBuzzerMediaPlayer.play(buz.soundId, onPlayerStateChange = { newValue ->
                        isPlaying.value = newValue
                    })
                },
                enabled = !isPlaying.value,
            ) {
                Text(stringResource(buz.nameRes))
            }
        }
    }
}
