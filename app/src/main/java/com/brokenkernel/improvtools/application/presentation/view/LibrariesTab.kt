package com.brokenkernel.improvtools.application.presentation.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.application.navigation.ImprovToolsDestination
import com.brokenkernel.improvtools.components.sidecar.navigation.ImprovToolsNavigationGraph
import com.mikepenz.aboutlibraries.ui.compose.android.produceLibraries
import com.mikepenz.aboutlibraries.ui.compose.m3.LibrariesContainer
import com.mikepenz.aboutlibraries.ui.compose.variant.LibraryBadges

@ImprovToolsDestination<ImprovToolsNavigationGraph>
@Composable
internal fun LibrariesTab() {
    val libraries by produceLibraries(R.raw.aboutlibraries)
    LibrariesContainer(
        modifier = Modifier.fillMaxSize(),
        badges = LibraryBadges(
            version = true,
            author = true,
            description = true,
            license = true,
            funding = true,
        ),
        libraries = libraries,
    )
}
