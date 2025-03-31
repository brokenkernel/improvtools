package com.brokenkernel.improvtools.settings.presentation.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.brokenkernel.improvtools.R

@Composable
fun SettingsScreen() {
    var shouldAllowSuggestionReuse: Boolean by remember { mutableStateOf(false) }

    // TODO: figure out how to make this persistent
    // TODO: figure out how to make this available to the other features

    Column {
        Text(
            stringResource(R.string.suggestions_activity_title),
            style = MaterialTheme.typography.titleLarge
        )
        Row() {
            Text("Allow Reuse")
            Checkbox(
                checked = shouldAllowSuggestionReuse,
                onCheckedChange = {
                    shouldAllowSuggestionReuse = it
                },
                enabled = false, // since this is useless for now, disable
            )
        }
    }

}