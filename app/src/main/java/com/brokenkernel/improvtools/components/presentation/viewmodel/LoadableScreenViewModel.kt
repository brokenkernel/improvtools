package com.brokenkernel.improvtools.components.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class LoadableScreenViewModel(private val loader: () -> Unit) :
    ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
        .onStart {
            doLoad()
            emit(_isLoading.value)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Companion.Eagerly,
            initialValue = false,
        )

    fun doLoad() {
        _isLoading.value = true
        loader()

        _isLoading.value = false
    }

    companion object {
        fun Factory(loader: () -> Unit): ViewModelProvider.Factory {
            return viewModelFactory {
                initializer {
                    LoadableScreenViewModel(loader)
                }
            }
        }
    }
}
