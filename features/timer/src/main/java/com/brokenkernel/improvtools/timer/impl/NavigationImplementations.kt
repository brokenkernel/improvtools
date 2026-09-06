package com.brokenkernel.improvtools.timer.impl

import androidx.navigation3.runtime.EntryProviderScope
import com.brokenkernel.improvtools.coreinfra.ImprovToolsNavigationKey
import com.brokenkernel.improvtools.timer.api.TimerNavigationKey

public fun EntryProviderScope<ImprovToolsNavigationKey>.timerScreenEntryBuilder() {
    entry<TimerNavigationKey> {
    }
}
