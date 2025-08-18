package com.brokenkernel.improvtools.tonguetwister.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.LocalContext
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.components.FilledButton
import androidx.glance.appwidget.components.Scaffold
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.provideContent
import androidx.glance.text.Text
import com.brokenkernel.improvtools.tonguetwister.R
import com.brokenkernel.improvtools.tonguetwister.data.TongueTwisterDatum

// TODO: move glance widget to its own module
// TODO: create base module with theme and base ImprovToolsGlanceThem etc that this depends on
internal class TongueTwisterWidget : GlanceAppWidget() {
    override val sizeMode: SizeMode = SizeMode.Exact // maybe 'Responsive' is better, but good enough for now

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            GlanceTheme {
                Scaffold {
                    RandomTongueTwister()
                }
            }
        }
    }
}

@Composable
internal fun RandomTongueTwister(modifier: GlanceModifier = GlanceModifier) {
    val shufData = TongueTwisterDatum.shuffled()
    var whichTT by remember { mutableIntStateOf(0) }

    LazyColumn(modifier = modifier) {
        item {
            Text(
                shufData[whichTT].asRawText(),
            )
        }
        item {
            FilledButton(
                text = LocalContext.current.getString(R.string.glance_another_tt),
                onClick = { whichTT = (whichTT + 1) % shufData.size },
            )
        }
    }
}

@Preview
@Composable
internal fun RandomTongueTwisterPreview() {
    RandomTongueTwister()
}
