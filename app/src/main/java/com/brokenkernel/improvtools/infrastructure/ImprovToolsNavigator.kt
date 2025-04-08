package com.brokenkernel.improvtools.infrastructure

import android.util.Log
import com.brokenkernel.improvtools.application.data.model.NavigableScreens
import kotlinx.coroutines.flow.MutableStateFlow

internal class ImprovToolsNavigator {
    var destination: MutableStateFlow<NavigableScreens> = MutableStateFlow(NavigableScreens.SuggestionGenerator)

    fun navigateTo(dest: NavigableScreens) {
        Log.w("HAAAALO", "navigating to $dest")
//        if (dest != destination.value) {
            this.destination.value = dest
//        }
    }
}