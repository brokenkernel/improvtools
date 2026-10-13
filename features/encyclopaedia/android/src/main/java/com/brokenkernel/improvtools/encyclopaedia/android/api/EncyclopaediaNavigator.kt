package com.brokenkernel.improvtools.encyclopaedia.android.api

import androidx.navigation3.runtime.EntryProviderScope
import com.brokenkernel.improvtools.coreinfra.ImprovToolsNavigationKey
import com.brokenkernel.improvtools.encyclopaedia.android.games.view.GamesTab

public fun EntryProviderScope<ImprovToolsNavigationKey>.encyclopaediaScreensEntryBuilderPart2(
    goTo: (ImprovToolsNavigationKey) -> Unit,
) {
    entry<GamesPageNavigationKey> {
        GamesTab(
            goTo = goTo,
        )
    }
}
