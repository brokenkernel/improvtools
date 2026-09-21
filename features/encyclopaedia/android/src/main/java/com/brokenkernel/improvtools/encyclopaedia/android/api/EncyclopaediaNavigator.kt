package com.brokenkernel.improvtools.encyclopaedia.android.api

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.brokenkernel.improvtools.coreinfra.ImprovToolsNavigationKey

public object EncyclopaediaNavigator {
    public fun goToEmotionTab(
        backstack: SnapshotStateList<ImprovToolsNavigationKey>, // TODO
    ) {
        backstack.add(EmotionsPageNavigationKey)
    }
}
