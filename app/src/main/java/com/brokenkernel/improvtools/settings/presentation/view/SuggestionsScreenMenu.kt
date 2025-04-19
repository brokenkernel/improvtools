package com.brokenkernel.improvtools.settings.presentation.view

import androidx.compose.foundation.layout.Box
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.settings.presentation.viewmodel.SettingsScreenViewModel

// TODO: consider moving into 'suggestions' package?
@Composable
internal fun SuggestionsScreenMenu(
    settingsScreenViewModel: SettingsScreenViewModel = hiltViewModel(),
    expanded: Boolean,
    onDismiss: () -> Unit,
) {
    val uiState by settingsScreenViewModel.uiState.collectAsStateWithLifecycle()

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismiss,
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
            // todo: figure out way to make this .8 reusable, and generic for name-and-checkbox
            Box(modifier = Modifier.weight(.8f)) {
                DropdownMenuItem(
                    text = { Text(stringResource(R.string.settings_allow_reuse)) },
                    onClick = {
                        settingsScreenViewModel.onClickUpdateShouldReuseSuggestions(
                            !uiState.shouldReuseSuggestions,
                        )
                    },
                )
            }
            Box {
                Switch(
                    checked = uiState.shouldReuseSuggestions,
                    onCheckedChange = { it ->
                        settingsScreenViewModel.onClickUpdateShouldReuseSuggestions(it)
                    },
                )
            }
        }
    }
}
