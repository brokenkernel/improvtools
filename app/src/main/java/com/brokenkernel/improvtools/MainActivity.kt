package com.brokenkernel.improvtools

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.brokenkernel.improvtools.application.data.model.ImprovToolsAppState
import com.brokenkernel.improvtools.application.data.model.NavigableRoute
import com.brokenkernel.improvtools.application.data.model.rememberImprovToolsAppState
import com.brokenkernel.improvtools.application.presentation.view.OuterContentForMasterScreen
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

        setContent {
            val improvToolsState: ImprovToolsAppState = rememberImprovToolsAppState()

            intent.categories
            // CATEGORY_LEANBACK_SETTINGS, CATEGORY_COMMUNAL_MODE, CATEGORY_PREFERENCE, CATEGORY_LEANBACK_LAUNCHER
            val initialRoute: NavigableRoute = if (intent.action != null) {
                val whichRoute = when (intent.action) {
                    ShowSuggestionsIntent -> NavigableRoute.SuggestionGeneratorRoute
                    ShowTimerIntent -> NavigableRoute.TimerRoute
                    ShowEncyclopaediaIntent -> NavigableRoute.EmotionPageRoute
                    Intent.ACTION_APPLICATION_PREFERENCES -> NavigableRoute.SettingsRoute
                    Intent.ACTION_VIEW_PERMISSION_USAGE -> NavigableRoute.PrivacyRoute // consider a separate privacy page eventualyl
                    Intent.ACTION_VIEW_PERMISSION_USAGE_FOR_PERIOD -> NavigableRoute.PrivacyRoute // consider a separate privacy page eventualyl
//                    ShowNotificationPreferencesIntent -> NavigableRoute.SettingsRoute
                    else -> NavigableRoute.SuggestionGeneratorRoute
                }
                setResult(RESULT_OK)
                whichRoute
            } else {
                NavigableRoute.SuggestionGeneratorRoute
            }

            // maybe ImprovToolsState, or at least a subset should be passed via LocalContent so it doesn't need to be threaded all over the place
            OuterContentForMasterScreen(
                improvToolsState = improvToolsState,
                initialRoute = initialRoute,
            )
        }
    }
}
