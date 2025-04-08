package com.brokenkernel.improvtools

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.brokenkernel.improvtools.application.data.model.NavigableScreens
import com.brokenkernel.improvtools.application.data.model.OuterContentForMasterScreen
import com.brokenkernel.improvtools.infrastructure.ImprovToolsNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val ShowSuggestionsIntent: String = "com.brokenkernel.improvtools.intents.ShowSuggestions"
private const val ShowTimerIntent: String = "com.brokenkernel.improvtools.intents.ShowTimer"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject internal lateinit var improvToolsNavigator: ImprovToolsNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            OuterContentForMasterScreen()
        }

        Log.e(APPLICATION_TAG, "I HAVE A LIFE")

        if (intent.action != null) {
            val whichRoute = when (intent.action) {
                ShowSuggestionsIntent -> NavigableScreens.SuggestionGenerator
                ShowTimerIntent -> NavigableScreens.Timer
                else -> NavigableScreens.SuggestionGenerator
            }
            Log.w(APPLICATION_TAG, "trying to go to route $whichRoute")
            improvToolsNavigator.navigateTo(whichRoute)
            setResult(RESULT_OK)
        }


    }
}

@Preview(showBackground = true)
@Composable
internal fun PreviewSuggestionPairList() {
    OuterContentForMasterScreen()
}

