package com.brokenkernel.improvtools

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavDestination
import androidx.navigation.compose.rememberNavController
import com.brokenkernel.improvtools.application.data.model.ImprovToolsAppState
import com.brokenkernel.improvtools.application.data.model.NavigableScreens
import com.brokenkernel.improvtools.application.data.model.rememberImprovToolsAppState
import com.brokenkernel.improvtools.application.presentation.view.OuterContentForMasterScreen
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import dagger.hilt.android.AndroidEntryPoint

private const val ShowSuggestionsIntent: String = "com.brokenkernel.improvtools.intents.ShowSuggestions"
private const val ShowTimerIntent: String = "com.brokenkernel.improvtools.intents.ShowTimer"
private const val ShowEncyclopaediaIntent: String = "com.brokenkernel.improvtools.intents.ShowEncyclopaedia"

// private const val ShowNotificationPreferencesIntent: String = "android.intent.action.NOTIFICATION_PREFERENCES"
// TODO: NotficiationPreferences

// TODO features of relationships
// power dynamics. Competitive, thearenative, enabled, scared, love, grudge, trusting suspicion

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // TODO: I just generally dislike how screen and rotues interact. I badly need to decouple
        // - place I might go
        // - how to get there
        // what to show about it
        // composables for that info
        // Its better, but not yet perfect.

        setContent {
//            intent.categories
            // CATEGORY_LEANBACK_SETTINGS, CATEGORY_COMMUNAL_MODE, CATEGORY_PREFERENCE, CATEGORY_LEANBACK_LAUNCHER
            val initialScreen: NavigableScreens = if (intent.action != null) {
                // TODO: this will have to change when I change how navigation works
                val whichRoute = when (intent.action) {
                    ShowSuggestionsIntent -> NavigableScreens.SuggestionGeneratorScreen
                    ShowTimerIntent -> NavigableScreens.TimerScreen
                    ShowEncyclopaediaIntent -> NavigableScreens.EmotionsPageScreen
                    Intent.ACTION_APPLICATION_PREFERENCES -> NavigableScreens.SettingsScreen
                    Intent.ACTION_VIEW_PERMISSION_USAGE -> NavigableScreens.PrivacyScreen // consider a separate privacy page eventualyl
                    Intent.ACTION_VIEW_PERMISSION_USAGE_FOR_PERIOD -> NavigableScreens.PrivacyScreen // consider a separate privacy page eventually
//                    ShowNotificationPreferencesIntent -> NavigableRoute.SettingsRoute
                    else -> NavigableScreens.SuggestionGeneratorScreen
                }
                setResult(RESULT_OK)
                // TODO? nav callback? something else?
                whichRoute
            } else {
                NavigableScreens.SuggestionGeneratorScreen
            }

            val titleState = rememberSaveable { mutableIntStateOf(initialScreen.titleResource) }
            val improvToolsState: ImprovToolsAppState = rememberImprovToolsAppState(
                titleState = titleState,
            )

            // maybe ImprovToolsState, or at least a subset should be passed via LocalContent so it doesn't need to be threaded all over the place
            OuterContentForMasterScreen(
                improvToolsState = improvToolsState,
                initialRoute = initialScreen.matchingRoutes.first(), // TODO: this is broken, but not yet ready
            )
        }
    }
}
