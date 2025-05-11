package com.brokenkernel.improvtools.suggestionGenerator.presentation.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.brokenkernel.components.view.HtmlText

@Composable
fun ExplanationBottomSheetTab(
    word: String,
    explanation: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        Text(
            word,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
        )
        HtmlText(explanation)
    }
}
