package com.brokenkernel.improvtools.tonguetwister.view

import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PauseCircle
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLocale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.brokenkernel.components.view.HtmlText
import com.brokenkernel.components.view.PageDots
import com.brokenkernel.improvtools.tonguetwister.R
import com.brokenkernel.improvtools.tonguetwister.data.TongueTwisterDatum
import com.brokenkernel.improvtools.tonguetwister.data.TongueTwisterItem
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.ExternalModuleGraph
import com.ramcosta.composedestinations.annotation.parameters.CodeGenVisibility
import java.util.Locale
import kotlinx.collections.immutable.ImmutableList

// see https://stackoverflow.com/questions/79493410/text-to-speech-in-jetpack-compose
@Composable
private fun rememberTextToSpeech(ttsLocale: Locale): MutableState<TextToSpeech?> {
    val context = LocalContext.current
    // TODO: make non-null, get injected?
    val tts = remember { mutableStateOf<TextToSpeech?>(null) }

    DisposableEffect(context) {
        val textToSpeech = TextToSpeech(context) { status ->
            // what to do on failure?
            if (status == TextToSpeech.SUCCESS) {
                tts.value?.language = ttsLocale
            }
        }
        tts.value = textToSpeech

        // don't shut down?
        onDispose {
            tts.value?.stop()
            tts.value?.shutdown()
        }
    }
    return tts
}

@Composable
private fun SingleTongueTwisterCard(
    ttitem: TongueTwisterItem,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxSize(),
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row {
                SelectionContainer {
                    HtmlText("<big>" + ttitem.asHtmlText() + "</big>")
                }
            }
            Row {
                // TODO lazy? injected? not sure this is the best.
                // TODO context? something else? per text?
                val tts =
                    rememberTextToSpeech(LocalLocale.current.platformLocale)
                var isSpeaking by remember { mutableStateOf(false) }

                val speechListener = object : UtteranceProgressListener() {
                    override fun onDone(p0: String?) {
                        isSpeaking = false
                    }

                    @Deprecated("use onError(String, Int) instead")
                    override fun onError(p0: String?) {
                        isSpeaking = false
                    }

                    override fun onStart(p0: String?) {
                        isSpeaking = true
                    }
                }
                tts.value?.setOnUtteranceProgressListener(speechListener)

                // TODO make toggleButton or PlayPause button?
                // TODO — ExtendedFloatingActionButton ?
                IconButton(
                    onClick = {
                        if (isSpeaking) {
                            tts.value?.stop()
                        } else {
                            tts.value?.speak(
                                ttitem.asRawText(),
                                TextToSpeech.QUEUE_FLUSH,
                                null,
                                ttitem.hashCode().toString(),
                            )
                        }
                    },
                ) {
                    Icon(
                        imageVector = if (isSpeaking) {
                            Icons.Default.PauseCircle
                        } else {
                            Icons.Default.PlayCircle
                        },
                        contentDescription = if (isSpeaking) {
                            stringResource(R.string.tts_pause)
                        } else {
                            stringResource(R.string.tts_listen)
                        },
                    )
                }
            }
            val explanation = ttitem.explanation
            if (explanation != null) {
                HorizontalDivider()
                val scrollState: ScrollState = rememberScrollState()
                Row(modifier = Modifier.verticalScroll(scrollState)) {
                    SelectionContainer {
                        HtmlText(ttitem.explanation)
                    }
                }
            }
        }
    }
}

@Composable
internal fun OuterTongueTwisterOutline(
    datum: ImmutableList<TongueTwisterItem>,
    modifier: Modifier = Modifier,
) {
    val pagerState = rememberPagerState(
        initialPage = 0, // figure out way to save this in viewmodel or similar(?). Like T&T
        pageCount = { datum.size },
    )
    Column(modifier = modifier) {
        HorizontalPager(
            pagerState,
            contentPadding = PaddingValues(2.dp),
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
        ) {
            SingleTongueTwisterCard(datum[pagerState.currentPage])
        }
        PageDots(
            pagerState.currentPage,
            pagerState.pageCount,
        )
    }
}

@Destination<ExternalModuleGraph>(
    visibility = CodeGenVisibility.PUBLIC,
)
@Composable
internal fun TongueTwisterTab() {
    // consider some way to save state for which item we're up to.
    // same concern as TipsAndTricks...
    OuterTongueTwisterOutline(
        TongueTwisterDatum,
    )
}

@Preview
@Composable
internal fun PreviewTongueTwisterTabPreview() {
    // consider some way to save state for which item we're up to.
    // same concern as TipsAndTricks...
    OuterTongueTwisterOutline(
        TongueTwisterDatum,
    )
}
