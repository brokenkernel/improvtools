package com.brokenkernel.improvtools.encyclopaedia.android.api

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation3.runtime.EntryProviderScope
import com.brokenkernel.improvtools.coreinfra.BackStack
import com.brokenkernel.improvtools.coreinfra.ImprovToolsNavigationKey
import com.brokenkernel.improvtools.encyclopaedia.android.games.view.GamesTab

public object EncyclopaediaNavigator {
    public fun goToEmotionTab(
        backstack: SnapshotStateList<ImprovToolsNavigationKey>, // TODO
    ) {
        backstack.add(EmotionsPageNavigationKey)
    }
}

public fun EntryProviderScope<ImprovToolsNavigationKey>.encyclopaediaScreensEntryBuilderPart2(
    backstack: BackStack,
) {
    entry<GamesPageNavigationKey> {
        GamesTab(
            backstack = backstack,
        )
    }
}
