package com.brokenkernel.improvtools.buzzer.view

import android.content.ContentResolver
import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import androidx.annotation.RawRes

internal class SimpleBuzzerMediaPlayer(context: Context) {
    private var mediaPlayer: MediaPlayer
    private var audioContext = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        context.createAttributionContext("audioPlayback")
    } else {
        context
    }

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            mediaPlayer = MediaPlayer(audioContext).apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .build(),
                )
            }
        } else {
            mediaPlayer = MediaPlayer().apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .build(),
                )
            }
        }
    }

    fun play(
        @RawRes resourceId: Int,
        onPlayerStateChange: (Boolean) -> Unit,
    ) {
        val uri = Uri.Builder()
            .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
            .authority(audioContext.packageName)
            .path(resourceId.toString())
            .build()
        mediaPlayer.setDataSource(audioContext, uri)
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
