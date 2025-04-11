package com.brokenkernel.improvtools

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.brokenkernel.improvtools.application.data.model.ImprovToolsAppState
import com.brokenkernel.improvtools.application.data.model.NavigableScreens
import com.brokenkernel.improvtools.application.data.model.rememberImprovToolsAppState
import com.brokenkernel.improvtools.application.presentation.view.OuterContentForMasterScreen
import dagger.hilt.android.AndroidEntryPoint

private const val ShowSuggestionsIntent: String = "com.brokenkernel.improvtools.intents.ShowSuggestions"
private const val ShowTimerIntent: String = "com.brokenkernel.improvtools.intents.ShowTimer"
private const val ShowEncyclopaediaIntent: String = "com.brokenkernel.improvtools.intents.ShowEncyclopaedia"

private const val ShowApplicationPreferencesIntent: String = "android.intent.action.APPLICATION_PREFERENCES"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val improvToolsState: ImprovToolsAppState = rememberImprovToolsAppState()
            if (intent.action != null) {
                val whichRoute = when (intent.action) {
                    ShowSuggestionsIntent -> NavigableScreens.SuggestionGenerator
                    ShowTimerIntent -> NavigableScreens.Timer
                    ShowEncyclopaediaIntent -> NavigableScreens.Encyclopaedia
                    ShowApplicationPreferencesIntent -> NavigableScreens.Settings
                    else -> NavigableScreens.SuggestionGenerator
                }
                improvToolsState.navigateTo(whichRoute)
                setResult(RESULT_OK)
            }

            OuterContentForMasterScreen()
        }




    }
}

@Preview(showBackground = true)
@Composable
internal fun PreviewSuggestionPairList() {
    OuterContentForMasterScreen()
}

