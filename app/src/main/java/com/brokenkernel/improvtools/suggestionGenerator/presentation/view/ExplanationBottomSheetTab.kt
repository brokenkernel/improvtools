package com.brokenkernel.improvtools.suggestionGenerator.presentation.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign

@Composable
fun ExplanationBottomSheetTab(word: String, explanation: String) {
    Column {
        Text(
            word,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
        )
        Text(explanation)
    }
}
