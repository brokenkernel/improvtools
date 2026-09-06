package com.brokenkernel.improvtools.application.navigation

import androidx.navigation3.runtime.EntryProviderScope
import com.brokenkernel.improvtools.application.api.AboutNavigationKey
import com.brokenkernel.improvtools.application.api.LibrariesNavigationKey
import com.brokenkernel.improvtools.application.api.PrivacyNavigationKey
import com.brokenkernel.improvtools.application.api.SettingsNavigationKey
import com.brokenkernel.improvtools.coreinfra.ImprovToolsNavigationKey

internal fun EntryProviderScope<ImprovToolsNavigationKey>.applicationScreensEntryBuilder() {
    entry<AboutNavigationKey> {
    }
    entry<LibrariesNavigationKey> {
    }
    entry<PrivacyNavigationKey> {
    }
    entry<SettingsNavigationKey> {
    }
}
