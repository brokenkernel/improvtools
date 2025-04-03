package com.brokenkernel.improvtools.settings.presentation.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.settings.presentation.viewmodel.SettingsScreenViewModel

@Composable
internal fun SettingsScreen(
    viewModel: SettingsScreenViewModel = hiltViewModel(),
) {

    val uiState by viewModel.uiState.collectAsState()

    Column {
        Text(
            stringResource(R.string.suggestions_activity_title),
            style = MaterialTheme.typography.titleLarge
        )
        Row {
            Box(modifier = Modifier.weight(.8f)) {
                Text(
                    stringResource(R.string.settings_allow_reuse),
                )
            }
            Box {
                Switch(
                    checked = uiState.shouldReuseSuggestions,
                    onCheckedChange = {
                        viewModel.onClickUpdateShouldReuseSuggestions(it)
                    },
                )
            }
        }
    }
}