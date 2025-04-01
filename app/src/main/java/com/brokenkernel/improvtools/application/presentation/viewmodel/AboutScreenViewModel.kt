package com.brokenkernel.improvtools.application.presentation.viewmodel

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PackageInfoFlags
import android.content.res.Resources
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.brokenkernel.improvtools.ImprovToolsApplication
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@Immutable
internal data class AboutScreenDebugData(
    // todo: pass further parsed data, but use this for now simplicity
    // todo: this is partly needed since we we need to impl c&p, email; and also to decouple UI from collection of debug data
    // todo: all of this is nullable since we need a default, figure out way to avoid needing default value
    val packageInfo: PackageInfo?,
    val isSafeMode: Boolean,
    val resources: Resources,
)


internal class AboutScreenViewModel(
    packageInfo: PackageInfo,  // toodo: figure nicer way to figure this out
    isSafeMode: Boolean,
    resources: Resources,
) : ViewModel() {

    // mutable shared flow to avoid init with default value
    val _uiState: MutableStateFlow<AboutScreenDebugData> =
        MutableStateFlow(AboutScreenDebugData(null, false, resources))
    val uiState: StateFlow<AboutScreenDebugData> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.value = AboutScreenDebugData(
                packageInfo,
                isSafeMode = isSafeMode,
                resources = resources,
            )
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as ImprovToolsApplication)
                val packageManager: PackageManager? = application.packageManager
                val resources: Resources = application.applicationContext.resources
                if (packageManager == null) {
                    // todo: error state: should handle this much better, leave for now for 'good' case handling
                    AboutScreenViewModel(
                        packageInfo = PackageInfo(),
                        isSafeMode = false,
                        resources = resources,
                    )
                } else {
                    val packageInfo: PackageInfo =
                        packageManager.getPackageInfo(application.packageName, PackageInfoFlags.of(0))
                    AboutScreenViewModel(
                        packageInfo = packageInfo,
                        isSafeMode = packageManager.isSafeMode,
                        resources = resources,
                    )
                }
            }
        }
    }
}