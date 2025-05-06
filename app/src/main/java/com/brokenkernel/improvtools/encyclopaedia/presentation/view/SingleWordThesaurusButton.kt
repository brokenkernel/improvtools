package com.brokenkernel.improvtools.encyclopaedia.presentation.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ReadMore
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.brokenkernel.components.view.SimpleTooltipWrapper
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.components.sidecar.preview.ImprovToolsAllPreviews
import com.brokenkernel.improvtools.ui.theme.ImprovToolsTheme

@Composable
fun SingleWordThesaurusButton(
    word: String,
    onNavigateToWord: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    SimpleTooltipWrapper(
        tooltip = stringResource(
            R.string.go_to_single_word_thesaurus_view,
            word,
        ),
        modifier = modifier,
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
private fun ExampleSingleWordThesaurusButtonPreview() {
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
