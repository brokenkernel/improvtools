package com.brokenkernel.improvtools.suggestions.api

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation3.runtime.EntryProviderScope
import com.brokenkernel.improvtools.coreinfra.BottomSheetSceneStrategy
import com.brokenkernel.improvtools.coreinfra.ImprovToolsNavigationKey
import com.brokenkernel.improvtools.coreinfra.ImprovToolsNavigator
import com.brokenkernel.improvtools.suggestions.view.AllWordsBottomSheetTab
import com.brokenkernel.improvtools.suggestions.view.ExplanationBottomSheetTab
import com.brokenkernel.improvtools.suggestions.view.SuggestionsTab

@OptIn(ExperimentalMaterial3Api::class)
public fun EntryProviderScope<ImprovToolsNavigationKey>.suggestionsScreenEntryBuilder(
    improvToolsNavigator: ImprovToolsNavigator,
) {
    entry<SuggestionsScreenNavigationKey> {
        SuggestionsTab(
            goTo = improvToolsNavigator::goTo,
        )
    }
    entry<SuggestionsExplanationNavigationKey>(
        metadata = BottomSheetSceneStrategy.bottomSheet(),
    ) { route ->
        ExplanationBottomSheetTab(
            word = route.word,
            explanation = route.explanation,
        )
    }
    entry<SuggestionsAllWordsForNavigationKey>(
        metadata = BottomSheetSceneStrategy.bottomSheet(),
    ) { route ->
        AllWordsBottomSheetTab(
            category = route.category,
        )
    }
}
