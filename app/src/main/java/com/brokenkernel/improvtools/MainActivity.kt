package com.brokenkernel.improvtools

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.brokenkernel.improvtools.application.data.model.NavigableScreens
import com.brokenkernel.improvtools.application.data.model.rememberImprovToolsAppState
import com.brokenkernel.improvtools.application.presentation.view.OuterContentForMasterScreen
import com.brokenkernel.improvtools.infrastructure.ImprovToolsNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val ShowSuggestionsIntent: String = "com.brokenkernel.improvtools.intents.ShowSuggestions"
private const val ShowTimerIntent: String = "com.brokenkernel.improvtools.intents.ShowTimer"
private const val ShowEncyclopaediaIntent: String = "com.brokenkernel.improvtools.intents.ShowEncyclopaedia"

private const val ShowApplicationPreferencesIntent: String = "android.intent.action.APPLICATION_PREFERENCES"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    @Inject
    internal lateinit var improvToolsNavigator: ImprovToolsNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
//            val abc = rememberImprovToolsAppState()
            if (intent.action != null) {
                val whichRoute = when (intent.action) {
                    ShowSuggestionsIntent -> NavigableScreens.SuggestionGenerator
                    ShowTimerIntent -> NavigableScreens.Timer
                    ShowEncyclopaediaIntent -> NavigableScreens.Encyclopaedia
                    ShowApplicationPreferencesIntent -> NavigableScreens.Settings
                    else -> NavigableScreens.SuggestionGenerator
                }
                improvToolsNavigator.navigateTo(whichRoute)
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

