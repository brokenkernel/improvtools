package com.brokenkernel.improvtools.application.navigation

import androidx.navigation3.runtime.EntryProviderScope
import com.brokenkernel.improvtools.application.api.AboutNavigationKey
import com.brokenkernel.improvtools.application.api.LibrariesNavigationKey
import com.brokenkernel.improvtools.application.api.PrivacyNavigationKey
import com.brokenkernel.improvtools.application.api.SettingsNavigationKey
import com.brokenkernel.improvtools.application.presentation.view.AboutTab
import com.brokenkernel.improvtools.application.presentation.view.LibrariesTab
import com.brokenkernel.improvtools.application.presentation.view.PrivacyTab
import com.brokenkernel.improvtools.coreinfra.ImprovToolsNavigationKey
import com.brokenkernel.improvtools.settings.presentation.view.SettingsTab
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

internal fun EntryProviderScope<ImprovToolsNavigationKey>.applicationScreensEntryBuilder(
    navigator: DestinationsNavigator
) {
    entry<AboutNavigationKey> {
        AboutTab(
            navigator = navigator,
        )
    }
    entry<LibrariesNavigationKey> {
        LibrariesTab()
    }
    entry<PrivacyNavigationKey> {
        PrivacyTab()
    }
    entry<SettingsNavigationKey> {
        SettingsTab()
    }
}
