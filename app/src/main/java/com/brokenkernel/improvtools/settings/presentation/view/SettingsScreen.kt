package com.brokenkernel.improvtools.settings.presentation.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.settings.presentation.viewmodel.SettingsScreenViewModel
import com.brokenkernel.improvtools.settings.utils.toTitleCase
import com.brokenkernel.improvtools.tipsandadvice.data.model.TipsAndAdviceViewModeUI

@Composable
internal fun SettingsScreen(viewModel: SettingsScreenViewModel = hiltViewModel(), onLaunchTitleCallback: () -> Unit) {
    // TODO: consider making a BaseScreenComposable or some such
    LaunchedEffect(Unit) {
        onLaunchTitleCallback()
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column {
        Text(
            stringResource(R.string.suggestions_activity_title),
            style = MaterialTheme.typography.titleLarge,
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
        Text(
            stringResource(R.string.privacy_settings_category),
            style = MaterialTheme.typography.titleLarge,
        )

        Row {
            Box(modifier = Modifier.weight(.8f)) {
                Text(
                    stringResource(R.string.settings_allow_analytics_cookie_storage),
                )
            }
            Box {
                Switch(
                    checked = uiState.allowAnalyticsCookieStorage,
                    onCheckedChange = {
                        viewModel.onClickUpdateAllowAnalyticsCookieStorage(it)
                    },
                )
            }
        }
        Text(
            stringResource(R.string.navigation_tips_and_advice),
            style = MaterialTheme.typography.titleLarge,
        )

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
