package com.brokenkernel.improvtools.settings.presentation.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.brokenkernel.components.view.EnumerationRadioSelection
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.application.navigation.ImprovToolsDestination
import com.brokenkernel.improvtools.components.sidecar.navigation.ImprovToolsNavigationGraph
import com.brokenkernel.improvtools.datastore.UserSettings.TimerHapticsMode
import com.brokenkernel.improvtools.datastore.UserSettings.TipsAndTricksViewMode
import com.brokenkernel.improvtools.encyclopaedia.android.tipsandadvice.model.TipsAndAdviceViewModeUI
import com.brokenkernel.improvtools.encyclopaedia.data.model.internalEnumValuebyTipsAndAdviceViewModeUI
import com.brokenkernel.improvtools.settings.presentation.viewmodel.SettingsScreenViewModel
import com.brokenkernel.improvtools.timer.data.model.TimerHapticsModeUI

@ImprovToolsDestination<ImprovToolsNavigationGraph>
@Composable
internal fun SettingsTab(viewModel: SettingsScreenViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column {
        Text(
            stringResource(R.string.suggestions_activity_title),
            style = MaterialTheme.typography.titleLarge,
        )
        ListItem(
            headlineContent = { Text(stringResource(R.string.settings_allow_reuse)) },
            trailingContent = {
                Switch(
                    checked = uiState.shouldReuseSuggestions,
                    onCheckedChange = {
                        viewModel.onClickUpdateShouldReuseSuggestions(it)
                    },
                )
            },
        )
        Text(
            stringResource(R.string.privacy_settings_category),
            style = MaterialTheme.typography.titleLarge,
        )
        ListItem(
            headlineContent = { Text(stringResource(R.string.settings_allow_analytics_cookie_storage)) },
            trailingContent = {
                Switch(
                    checked = uiState.allowAnalyticsCookieStorage,
                    onCheckedChange = {
                        viewModel.onClickUpdateAllowAnalyticsCookieStorage(it)
                    },
                )
            },
        )
        Text(
            stringResource(R.string.navigation_tips_and_advice),
            style = MaterialTheme.typography.titleLarge,
        )
        ListItem(
            headlineContent = { Text(stringResource(R.string.settings_tips_and_tricks_view_mode)) },
            trailingContent = {
                EnumerationRadioSelection<TipsAndAdviceViewModeUI, TipsAndTricksViewMode>(
                    onEnumerationSelection = { opt ->
                        viewModel.onClickUpdateTipsAndTricksViewMode(opt)
                    },
                    currentlySelected = uiState.tipsAndTricksViewMode,
                    uiToInternalMapping = { opt -> internalEnumValuebyTipsAndAdviceViewModeUI(opt) },
                )
            },
        )
        Text(
            stringResource(R.string.timer_settings_header),
            style = MaterialTheme.typography.titleLarge,
        )
        ListItem(
            headlineContent = { Text(stringResource(R.string.timer_haptics_mode)) },
            trailingContent = {
                EnumerationRadioSelection<TimerHapticsModeUI, TimerHapticsMode>(
                    onEnumerationSelection = { opt ->
                        viewModel.onClickUpdateTimerHapticsMode(opt)
                    },
                    uiToInternalMapping = { opt -> opt.internalEnumMatching },
                    currentlySelected = uiState.timerHapticsMode,
                )
            },
        )
    }
}
