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

    // Is there a way to avoid needing the the default here, shouldn't it come from settings provider initially ?
    private val _uiState = MutableStateFlow(SettingsScreenUIState.default())
    internal val uiState: StateFlow<SettingsScreenUIState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            settingsRepository.userSettingsFlow.collect { it ->
                _uiState.value = SettingsScreenUIState(
                    shouldReuseSuggestions = it.allowSuggestionsReuse
                )
            }
        }
    }

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