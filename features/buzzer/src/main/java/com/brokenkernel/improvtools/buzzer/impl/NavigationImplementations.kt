package com.brokenkernel.improvtools.buzzer.impl

import androidx.navigation3.runtime.EntryProviderScope
import com.brokenkernel.improvtools.buzzer.api.BuzzerNavigationKey
import com.brokenkernel.improvtools.buzzer.view.BuzzerTab
import com.brokenkernel.improvtools.coreinfra.ImprovToolsNavigationKey

public fun EntryProviderScope<ImprovToolsNavigationKey>.buzzerScreenEntryBuilder() {
    entry<BuzzerNavigationKey> {
        BuzzerTab()
    }
}
