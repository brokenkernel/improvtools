package com.brokenkernel.improvtools.encyclopaedia.presentation.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.brokenkernel.components.view.HtmlText
import com.brokenkernel.improvtools.encyclopaedia.data.GamesDatumTag

@Composable
internal fun SingleTagBottomTab(tag: GamesDatumTag) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            tag.label,
            style = MaterialTheme.typography.headlineLarge,
        )
        val description = tag.description
        if (description != null) {
            HtmlText(description)
        }
    }
}
