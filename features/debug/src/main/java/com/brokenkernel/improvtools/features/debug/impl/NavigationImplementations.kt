package com.brokenkernel.improvtools.features.debug.impl

import androidx.navigation3.runtime.EntryProviderScope
import com.brokenkernel.improvtools.coreinfra.ImprovToolsNavigationKey
import com.brokenkernel.improvtools.features.debug.api.DebugCollectorNavigationKey

public fun EntryProviderScope<ImprovToolsNavigationKey>.buzzerScreenEntryBuilder() {
    entry<DebugCollectorNavigationKey> {
    }
}
