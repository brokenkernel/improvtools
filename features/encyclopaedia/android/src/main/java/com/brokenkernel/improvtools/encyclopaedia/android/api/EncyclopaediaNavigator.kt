package com.brokenkernel.improvtools.encyclopaedia.android.api

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation3.runtime.EntryProviderScope
import com.brokenkernel.improvtools.coreinfra.BottomSheetSceneStrategy
import com.brokenkernel.improvtools.coreinfra.ImprovToolsNavigationKey
import com.brokenkernel.improvtools.encyclopaedia.android.components.SingleTagBottomTab
import com.brokenkernel.improvtools.encyclopaedia.android.games.view.GamesTab

@OptIn(ExperimentalMaterial3Api::class)
public fun EntryProviderScope<ImprovToolsNavigationKey>.encyclopaediaScreensEntryBuilderPart2(
    goTo: (ImprovToolsNavigationKey) -> Unit,
) {
    entry<GamesPageNavigationKey> {
        GamesTab(
            goTo = goTo,
        )
    }
    entry<SingleTagBottomTabNavigationKey>(
        metadata = BottomSheetSceneStrategy.bottomSheet(),
    ) { route ->
        SingleTagBottomTab(
            tag = route.tag,
        )
    }
}
