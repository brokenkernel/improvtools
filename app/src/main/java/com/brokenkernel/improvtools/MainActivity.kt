package com.brokenkernel.improvtools

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

private const val ShowApplicationPreferencesIntent: String = "android.intent.action.APPLICATION_PREFERENCES"
// TODO: NotficiationPreferenes

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val improvToolsState: ImprovToolsAppState = rememberImprovToolsAppState()

            if (intent.action != null) {
                val whichRoute = when (intent.action) {
                    ShowSuggestionsIntent -> NavigableRoute.SuggestionGeneratorRoute
                    ShowTimerIntent -> NavigableRoute.TimerRoute
                    ShowEncyclopaediaIntent -> NavigableRoute.EmotionPageRoute
                    ShowApplicationPreferencesIntent -> NavigableRoute.SettingsRoute
                    else -> NavigableRoute.SuggestionGeneratorRoute
                }
                improvToolsState.navigateTo(whichRoute)
                setResult(RESULT_OK)
            }

            // maybe ImprovToolsState, or at least a subset should be passed via LocalContent so it doesn't need to be threaded all over the place
            OuterContentForMasterScreen(improvToolsState = improvToolsState)
        }

    }
}
