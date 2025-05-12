package com.brokenkernel.improvtools

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import com.brokenkernel.improvtools.application.data.model.ImprovToolsAppState
import com.brokenkernel.improvtools.application.data.model.NavigableScreens
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
            val viewModel: MainActivityViewModel = hiltViewModel<MainActivityViewModel>()

//            intent.categories
            // CATEGORY_LEANBACK_SETTINGS, CATEGORY_COMMUNAL_MODE, CATEGORY_PREFERENCE, CATEGORY_LEANBACK_LAUNCHER
            val initialScreen: NavigableScreens = if (intent.action != null) {
                // TODO: this will have to change when I change how navigation works
                val whichRoute = when (intent.action) {
                    ShowSuggestionsIntent -> NavigableScreens.SuggestionGeneratorScreen
                    ShowTimerIntent -> NavigableScreens.TimerScreen
                    ShowEncyclopaediaIntent -> NavigableScreens.EmotionsPageScreen
                    Intent.ACTION_APPLICATION_PREFERENCES -> NavigableScreens.SettingsScreen
                    // TODO: consider a separate privacy page
                    Intent.ACTION_VIEW_PERMISSION_USAGE -> NavigableScreens.PrivacyScreen
                    Intent.ACTION_VIEW_PERMISSION_USAGE_FOR_PERIOD -> NavigableScreens.PrivacyScreen
//                    ShowNotificationPreferencesIntent -> NavigableRoute.SettingsRoute
                    else -> NavigableScreens.SuggestionGeneratorScreen
                }
                setResult(RESULT_OK)
                // TODO? nav callback? something else?
                whichRoute
            } else {
                NavigableScreens.SuggestionGeneratorScreen
            }

            val improvToolsState: ImprovToolsAppState = rememberImprovToolsAppState(
                initialTitle = initialScreen.titleResource,
            )

            // maybe ImprovToolsState, or at least a subset should be passed via LocalContent so it doesn't need to be threaded all over the place
            OuterContentForMasterScreen(
                improvToolsState = improvToolsState,
                initialRoute = initialScreen.matchingRoute,
            )
        }
    }
}
