package com.brokenkernel.improvtools.application.navigation

import androidx.compose.runtime.snapshots.SnapshotStateList
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

internal fun EntryProviderScope<ImprovToolsNavigationKey>.applicationScreensEntryBuilder(
    backstack: SnapshotStateList<ImprovToolsNavigationKey>,
) {
    entry<AboutNavigationKey> {
        AboutTab(
            onGoToPrivacyTab = {
                backstack.add(PrivacyNavigationKey)
            },
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
