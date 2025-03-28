package com.brokenkernel.improvtools.application.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.brokenkernel.improvtools.application.presentation.uistate.ApplicationNavigationState
import com.brokenkernel.improvtools.suggestionGenerator.presentation.uistate.SuggestionScreenUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NavigationDrawerViewModel : ViewModel() {


    private val _uiState = MutableStateFlow(ApplicationNavigationState())
    internal val uiState: StateFlow<ApplicationNavigationState> = _uiState.asStateFlow()

}
