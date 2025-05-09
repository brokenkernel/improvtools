package com.brokenkernel.improvtools.suggestionGenerator.api

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.brokenkernel.improvtools.application.data.model.ImprovToolsAppState
import com.brokenkernel.improvtools.application.data.model.NavigableRoute
import com.brokenkernel.improvtools.application.data.model.NavigableScreens
import com.brokenkernel.improvtools.application.presentation.view.LaunchWrapper
import com.brokenkernel.improvtools.encyclopaedia.EncyclopaediaSectionNavigation
import com.brokenkernel.improvtools.settings.presentation.view.SuggestionsTabMenu
import com.brokenkernel.improvtools.suggestionGenerator.presentation.view.ExplanationBottomSheetTab
import com.brokenkernel.improvtools.suggestionGenerator.presentation.view.SuggestionsTab

internal fun NavGraphBuilder.suggestionsRoutes(improvToolsAppState: ImprovToolsAppState) {
    composable<NavigableRoute.SuggestionGeneratorRoute> {
        LaunchWrapper(
            onLaunchCallback = {
                improvToolsAppState.setScaffoldData(
                    newTitle = NavigableScreens.SuggestionGeneratorScreen.titleResource,
                    newExtraMenu = {
                        SuggestionsTabMenu(
                            expanded = improvToolsAppState.extraMenuExpandedState,
                            onDismiss = {
                                improvToolsAppState.extraMenuExpandedState =
                                    !improvToolsAppState.extraMenuExpandedState
                            },
                        )
                    },
                )
            },
        ) {
            SuggestionsTab(
                onNavigateToEmotionsInfographic = {
                    improvToolsAppState.navigateTo(NavigableRoute.EmotionPageRoute)
                },
                onNavigateToWord = {
                    EncyclopaediaSectionNavigation.navigateToThesaurusWord(improvToolsAppState, it)
                },
                onNavigateToExplanation = { word: String, explanation: String ->
                    improvToolsAppState.setBottomSheetTo {
                        ExplanationBottomSheetTab(word, explanation)
                    }
                },
            )
        }
    }
}
