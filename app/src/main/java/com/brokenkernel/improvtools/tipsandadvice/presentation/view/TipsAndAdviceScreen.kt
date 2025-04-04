package com.brokenkernel.improvtools.tipsandadvice.presentation.view

import android.widget.TextView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChangeCircle
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.tipsandadvice.presentation.viewmodel.TipsAndAdviceViewModel


@Composable
fun HtmlText(html: String, modifier: Modifier = Modifier) {
    AndroidView(
        modifier = modifier,
        factory = { context -> TextView(context) },
        update = { it.text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_COMPACT) }
    )
}

@Composable
internal fun TipsAndAdviceScreen(viewModel: TipsAndAdviceViewModel = hiltViewModel()) {
    val tipOfTheDay by viewModel.uiState.collectAsState()

    Column {
        Row {
            SelectionContainer {
                Text(tipOfTheDay.title)
            }
        }
        Row {
            SelectionContainer() {
                HtmlText(tipOfTheDay.content)
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            // TODO: maybe make filledtonalbutton composable with ChangeCircle its own component for myself?
            // TODO: maybe this should be a floating action button. Maybe it should at least be pinned to the bottom
            FilledTonalButton(
                onClick = { viewModel.loadNewTOTD() },

                ) {
                Row {
                    Icon(
                        Icons.Filled.ChangeCircle,
                        contentDescription = stringResource(R.string.tips_and_advice_reload)
                    )
                    Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_icon_text_for_button)))
                    Text(text = stringResource(R.string.tips_and_advice_reload))
                }
            }
        }
    }
}