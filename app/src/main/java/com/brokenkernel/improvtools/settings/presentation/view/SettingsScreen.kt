package com.brokenkernel.improvtools.settings.presentation.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.settings.presentation.viewmodel.SettingsScreenViewModel

@Composable
internal fun SettingsScreen(
    viewModel: SettingsScreenViewModel = viewModel(factory = SettingsScreenViewModel.Factory),
) {

    val uiState by viewModel.uiState.collectAsState()

    // TODO: figure out how to make this available to the other features

    Column {
        Text(
            stringResource(R.string.suggestions_activity_title),
            style = MaterialTheme.typography.titleLarge
        )
        Row() {
            Text("Allow Reuse")
            Checkbox(
                checked = uiState.shouldReuseSuggestions,
                onCheckedChange = {
                    viewModel.onClickUpdateShouldReuseSuggestions(it)
                },
            )
        }
    }
}