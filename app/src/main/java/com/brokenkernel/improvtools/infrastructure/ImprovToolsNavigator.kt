package com.brokenkernel.improvtools.infrastructure

import android.os.Bundle
import com.brokenkernel.improvtools.application.data.model.NavigableScreens
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import kotlinx.coroutines.flow.MutableStateFlow

internal class ImprovToolsNavigator {
    var destination: MutableStateFlow<NavigableScreens> = MutableStateFlow(NavigableScreens.SuggestionGenerator)

    fun navigateTo(dest: NavigableScreens) {
        val firebaseBundle = Bundle()
        firebaseBundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, dest.route.toString())
        firebaseBundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, dest::class.qualifiedName)
        Firebase.analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, firebaseBundle)
        Firebase.analytics.setDefaultEventParameters(firebaseBundle)
        if (dest != destination.value) {
            this.destination.value = dest
        }
    }
}