package com.brokenkernel.improvtools.tonguetwister.impl

import androidx.navigation3.runtime.EntryProviderScope
import com.brokenkernel.improvtools.coreinfra.ImprovToolsNavigationKey
import com.brokenkernel.improvtools.tonguetwister.api.TongueTwisterNavigationKey
import com.brokenkernel.improvtools.tonguetwister.view.TongueTwisterTab

public fun EntryProviderScope<ImprovToolsNavigationKey>.tonguetwisterScreenEntryBuilder() {
    entry<TongueTwisterNavigationKey> {
        TongueTwisterTab()
    }
}
