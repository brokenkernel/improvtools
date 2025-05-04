package com.brokenkernel.improvtools.encyclopaedia.presentation.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.brokenkernel.improvtools.encyclopaedia.data.model.GamesDatumTag

@Composable
internal fun SingleTagBottomTab(tag: GamesDatumTag) {
    Column {
        Text(
            tag.label,
            style = MaterialTheme.typography.headlineLarge,
        )
        Text(tag.description)
    }
}
