package com.brokenkernel.improvtools.settings.presentation.view

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.settings.presentation.viewmodel.SettingsScreenViewModel

@Composable
internal fun SuggestionsTabMenu(
    expanded: Boolean,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    settingsScreenViewModel: SettingsScreenViewModel = hiltViewModel(),
) {
    val uiState by settingsScreenViewModel.uiState.collectAsStateWithLifecycle()

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismiss,
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier.semantics(
                mergeDescendants = true,
            ) {
                isTraversalGroup = true
            }
                .toggleable(
                    value = uiState.shouldReuseSuggestions,
                    role = Role.Checkbox,
                    onValueChange = {
                        settingsScreenViewModel.onClickUpdateShouldReuseSuggestions(
                            !uiState.shouldReuseSuggestions,
                        )
                    },
                ),
        ) {
            DropdownMenuItem(
                text = { Text(stringResource(R.string.settings_allow_reuse)) },
                trailingIcon = {
                    Switch(
                        checked = uiState.shouldReuseSuggestions,
                        onCheckedChange = {
                            settingsScreenViewModel.onClickUpdateShouldReuseSuggestions(
                                !uiState.shouldReuseSuggestions,
                            )
                        },
                    )
                },
                onClick = {
                    settingsScreenViewModel.onClickUpdateShouldReuseSuggestions(
                        !uiState.shouldReuseSuggestions,
                    )
                },
            )
        }
    }
}
