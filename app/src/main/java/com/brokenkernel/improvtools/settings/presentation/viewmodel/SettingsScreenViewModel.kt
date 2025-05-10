package com.brokenkernel.improvtools.settings.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brokenkernel.improvtools.datastore.UserSettings
import com.brokenkernel.improvtools.encyclopaedia.data.model.TipsAndAdviceViewModeUI
import com.brokenkernel.improvtools.infrastructure.ConsentManagement
import com.brokenkernel.improvtools.settings.data.repository.SettingsRepository
import com.brokenkernel.improvtools.settings.presentation.uistate.SettingsScreenUIState
import com.brokenkernel.improvtools.timer.data.model.TimerHapticsModeUI
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@HiltViewModel
internal class SettingsScreenViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
) :
    ViewModel() {

    // Is there a way to avoid needing the the default here, shouldn't it come from settings provider initially ?
    private val _uiState = MutableStateFlow(SettingsScreenUIState.default())
    internal val uiState: StateFlow<SettingsScreenUIState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            settingsRepository.userSettingsFlow.collectLatest { it ->
                _uiState.value = SettingsScreenUIState(
                    shouldReuseSuggestions = it.allowSuggestionsReuse,
                    allowAnalyticsCookieStorage = it.allowAnalyticsCookieStorage,
                    tipsAndTricksViewMode = it.tipsAndTricksViewMode,
                    timerHapticsMode = it.hapticFeedbackTimerMode,
                )
            }
        }
        viewModelScope
    }

    fun onClickUpdateShouldReuseSuggestions(newState: Boolean) {
        viewModelScope.launch {
            settingsRepository.updateAllowSuggestionsReuse(newState)
        }
        _uiState.value = _uiState.value.copy(shouldReuseSuggestions = newState)
    }

    fun onClickUpdateTipsAndTricksViewMode(newState: TipsAndAdviceViewModeUI) {
        val internalNewState: UserSettings.TipsAndTricksViewMode = newState.internalEnumMatching
        viewModelScope.launch {
            settingsRepository.updateTipsAndTricksViewMode(internalNewState)
        }
        _uiState.value = _uiState.value.copy(tipsAndTricksViewMode = internalNewState)
    }

    fun onClickUpdateAllowAnalyticsCookieStorage(newState: Boolean) {
        viewModelScope.launch {
            settingsRepository.updateAllowAnalyticsCookieStorage(newState)
        }

        _uiState.value = _uiState.value.copy(allowAnalyticsCookieStorage = newState)

        // TODO: this really should be handled by an observer but I can't figure out how to do this right now
        ConsentManagement.configureConsentForFirebase(newState)
    }

    fun onClickUpdateTimerHapticsMode(newState: TimerHapticsModeUI) {
        val internalNewState = newState.internalEnumMatching
        viewModelScope.launch {
            settingsRepository.updateTimerHapticsMode(internalNewState)
        }

        _uiState.value = _uiState.value.copy(timerHapticsMode = internalNewState)
    }
}
