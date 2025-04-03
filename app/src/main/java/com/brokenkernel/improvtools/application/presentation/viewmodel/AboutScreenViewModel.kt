package com.brokenkernel.improvtools.application.presentation.viewmodel

import android.content.pm.PackageInfo
import android.content.res.Resources
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brokenkernel.improvtools.application.data.repository.AboutScreenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@Immutable
internal data class AboutScreenDebugData(
    // todo: pass further parsed data, but use this for now simplicity
    // todo: this is partly needed since we we need to impl c&p, email; and also to decouple UI from collection of debug data
    val packageInfo: PackageInfo?,
    val isSafeMode: Boolean,
    val resources: Resources,
)


@HiltViewModel
internal class AboutScreenViewModel @Inject constructor(
    aboutScreenRepository: AboutScreenRepository,
) : ViewModel() {

    // mutable shared flow to avoid init with default value
    val _uiState: MutableStateFlow<AboutScreenDebugData> =
        MutableStateFlow(AboutScreenDebugData(null, false, aboutScreenRepository.resources))
    val uiState: StateFlow<AboutScreenDebugData> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.value = AboutScreenDebugData(
                aboutScreenRepository.packageInfo,
                isSafeMode = aboutScreenRepository.isSafeMode,
                resources = aboutScreenRepository.resources,
            )
        }
    }
}