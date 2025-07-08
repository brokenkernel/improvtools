package com.brokenkernel.improvtools.encyclopaedia.presentation.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.brokenkernel.components.view.EnumerationRadioSelection
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.datastore.UserSettings.TipsAndTricksViewMode
import com.brokenkernel.improvtools.encyclopaedia.android.tipsandadvice.model.TipsAndAdviceViewModeUI
import com.brokenkernel.improvtools.encyclopaedia.data.model.internalEnumValuebyTipsAndAdviceViewModeUI
import com.brokenkernel.improvtools.settings.presentation.viewmodel.SettingsScreenViewModel

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
            EnumerationRadioSelection<TipsAndAdviceViewModeUI, TipsAndTricksViewMode>(
                onEnumerationSelection = { opt ->
                    viewModel.onClickUpdateTipsAndTricksViewMode(opt)
                },
                uiToInternalMapping = { opt -> internalEnumValuebyTipsAndAdviceViewModeUI(opt) },
                currentlySelected = uiState.tipsAndTricksViewMode,
            )
        }
    }
}
