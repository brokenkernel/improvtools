package com.brokenkernel.improvtools.settings.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.brokenkernel.improvtools.ImprovToolsApplication
import com.brokenkernel.improvtools.settings.data.repository.SettingsRepository
import com.brokenkernel.improvtools.settings.presentation.uistate.SettingsScreenUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class SettingsScreenViewModel(private val settingsRepository: SettingsRepository) :
    ViewModel() {

    // todo: need to read initial value from preferences
    private val _uiState = MutableStateFlow(SettingsScreenUIState(false))
    internal val uiState: StateFlow<SettingsScreenUIState> = _uiState.asStateFlow()

//    internal val _shouldReuseSuggestions = MutableStateFlow(Boolean)
//    internal val shouldReuseSuggestions = _shouldReuseSuggestions.asStateFlow()

    fun onClickUpdateShouldReuseSuggestions(newState: Boolean) {
        viewModelScope.launch {
            settingsRepository.updateAllowSuggestionsReuse(newState)
        }

        // todo: likely should read from preferences
        _uiState.value = _uiState.value.copy(shouldReuseSuggestions = newState)
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as ImprovToolsApplication)
                val settingsRepository = application.container.settingsRepository
                SettingsScreenViewModel(settingsRepository)
            }
        }
    }
}