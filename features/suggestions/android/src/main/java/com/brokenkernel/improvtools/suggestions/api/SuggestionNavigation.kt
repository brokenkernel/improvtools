package com.brokenkernel.improvtools.suggestions.api

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation3.runtime.EntryProviderScope
import com.brokenkernel.improvtools.coreinfra.BottomSheetSceneStrategy
import com.brokenkernel.improvtools.coreinfra.ImprovToolsNavigationKey
import com.brokenkernel.improvtools.suggestions.view.AllWordsBottomSheetTab
import com.brokenkernel.improvtools.suggestions.view.ExplanationBottomSheetTab

@OptIn(ExperimentalMaterial3Api::class)
public fun EntryProviderScope<ImprovToolsNavigationKey>.suggestionsScreenEntryBuilder() {
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
