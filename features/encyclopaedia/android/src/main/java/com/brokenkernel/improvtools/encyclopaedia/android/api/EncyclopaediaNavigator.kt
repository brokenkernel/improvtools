package com.brokenkernel.improvtools.encyclopaedia.android.api

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation3.runtime.EntryProviderScope
import com.brokenkernel.improvtools.coreinfra.BottomSheetSceneStrategy
import com.brokenkernel.improvtools.coreinfra.ImprovToolsNavigationKey
import com.brokenkernel.improvtools.encyclopaedia.android.components.SingleTagBottomTab
import com.brokenkernel.improvtools.encyclopaedia.android.emotions.EmotionsTab
import com.brokenkernel.improvtools.encyclopaedia.android.games.view.GamesTab
import com.brokenkernel.improvtools.encyclopaedia.android.glossary.view.GlossaryTab
import com.brokenkernel.improvtools.encyclopaedia.android.people.PeopleTab

@OptIn(ExperimentalMaterial3Api::class)
public fun EntryProviderScope<ImprovToolsNavigationKey>.encyclopaediaScreensEntryBuilderPart2(
    goTo: (ImprovToolsNavigationKey) -> Unit,
) {
    entry<GamesPageNavigationKey> {
        GamesTab(
            goTo = goTo,
        )
    }
    entry<GlossaryPageNavigationKey> {
        GlossaryTab()
    }
    entry<EmotionsPageNavigationKey> {
        EmotionsTab()
    }
    entry<PeoplePageNavigationKey> {
        PeopleTab()
    }
    entry<SingleTagBottomTabNavigationKey>(
        metadata = BottomSheetSceneStrategy.bottomSheet(),
    ) { route ->
        SingleTagBottomTab(
            tag = route.tag,
        )
    }
}
