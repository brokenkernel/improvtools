package com.brokenkernel.improvtools.encyclopaedia.presentation.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ReadMore
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.components.sidecar.preview.ImprovToolsAllPreviews
import com.brokenkernel.improvtools.ui.theme.ImprovToolsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingleWordThesaurusButton(
    word: String,
    onNavigateToWord: (String) -> Unit,
    enabled: Boolean = true,
) {
    TooltipBox(
        positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
        state = rememberTooltipState(),
        tooltip = {
            PlainTooltip {
                Text(
                    stringResource(
                        R.string.go_to_single_word_thesaurus_view,
                        word,
                    ),
                )
            }
        },
    ) {
        IconButton(
            onClick = {
                onNavigateToWord(word)
            },
            enabled = enabled,
        ) {
            Icon(
                Icons.AutoMirrored.Default.ReadMore,
                contentDescription = stringResource(
                    R.string.go_to_single_word_thesaurus_view,
                    word,
                ),
            )
        }
    }
}

@ImprovToolsAllPreviews
@Composable
fun ExampleSingleWordThesaurusButton() {
    ImprovToolsTheme {
        Surface {
            Column {
                Text("enabled")
                SingleWordThesaurusButton(word = "induldge", onNavigateToWord = {})
                Text("disabled")
                SingleWordThesaurusButton(word = "induldge", onNavigateToWord = {}, enabled = false)
            }
        }
    }
}
