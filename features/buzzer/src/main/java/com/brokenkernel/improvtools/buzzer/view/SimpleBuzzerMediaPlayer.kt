package com.brokenkernel.improvtools.buzzer.view

import android.content.ContentResolver
import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import androidx.annotation.RawRes

internal class SimpleBuzzerMediaPlayer(private val context: Context) {
    private val mediaPlayer = MediaPlayer().apply {
        setAudioAttributes(
            AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build(),
        )
    }

    fun play(
        @RawRes resourceId: Int,
        onPlayerStateChange: (Boolean) -> Unit,
    ) {
        val uri = Uri.Builder()
            .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
            .authority(context.packageName)
            .path(resourceId.toString())
            .build()
        mediaPlayer.setDataSource(context, uri)
        mediaPlayer.setOnPreparedListener { m ->
            onPlayerStateChange(true)
            m.start()
        }
        mediaPlayer.setOnCompletionListener { m ->
            onPlayerStateChange(false)
            m.reset()
        }
        mediaPlayer.prepareAsync()
    }
}
