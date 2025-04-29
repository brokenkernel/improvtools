package com.brokenkernel.improvtools.suggestionGenerator.presentation.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ReadMore
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.components.sidecar.preview.ImprovToolsAllPreviews
import com.brokenkernel.improvtools.ui.theme.ImprovToolsTheme

@Composable
fun SingleWordThesaurusButton(word: String, onNavigateToWord: (String) -> Unit) {
    IconButton(
        onClick = {
            onNavigateToWord(word)
        },
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

@ImprovToolsAllPreviews
@Composable
fun ExampleSingleWordThesaurusButton() {
    ImprovToolsTheme {
        Surface {
            SingleWordThesaurusButton(word = "induldge", onNavigateToWord = {})
        }
    }
}
