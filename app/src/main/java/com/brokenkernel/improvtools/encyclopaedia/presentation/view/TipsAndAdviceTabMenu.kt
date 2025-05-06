package com.brokenkernel.improvtools.encyclopaedia.presentation.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.encyclopaedia.data.model.TipsAndAdviceViewModeUI
import com.brokenkernel.improvtools.settings.presentation.viewmodel.SettingsScreenViewModel
import com.brokenkernel.improvtools.settings.utils.toTitleCase

@Composable
internal fun TipsAndAdviceTabMenu(
    expanded: Boolean,
    onDismiss: () -> Unit,
    viewModel: SettingsScreenViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismiss,
    ) {
        Row {
            Box(modifier = Modifier.weight(.8f)) {
                Text(
                    stringResource(R.string.settings_tips_and_tricks_view_mode),
                )
            }
            Column(modifier = Modifier.selectableGroup()) {
                TipsAndAdviceViewModeUI.entries.forEach { opt ->
                    Row(
                        Modifier
                            .selectable(
                                selected = (
                                    opt.internalEnumsMatching.contains(
                                        uiState.tipsAndTricksViewMode,
                                    )
                                    ),
                                onClick = { viewModel.onClickUpdateTipsAndTricksViewMode(opt) },
                                role = Role.RadioButton,
                            ),
                    ) {
                        RadioButton(
                            selected = (
                                opt.internalEnumsMatching.contains(
                                    uiState.tipsAndTricksViewMode,
                                )
                                ),
                            onClick = null,
                        )
                        Text(
                            text = opt.name.toTitleCase("_", " "),
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                }
            }
        }
    }
}
