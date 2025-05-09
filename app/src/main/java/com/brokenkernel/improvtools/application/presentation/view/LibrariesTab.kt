package com.brokenkernel.improvtools.application.presentation.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.brokenkernel.improvtools.components.sidecar.navigation.ImprovToolsNavigationGraph
import com.mikepenz.aboutlibraries.ui.compose.m3.LibrariesContainer
import com.ramcosta.composedestinations.annotation.Destination

@Destination<ImprovToolsNavigationGraph>(
    wrappers = [ SetScaffoldStateWrapper::class ],
)
@Composable
internal fun LibrariesTab() {
    LibrariesContainer(
        modifier = Modifier.fillMaxSize(),
        showAuthor = true,
        showDescription = true,
        showVersion = true,
        showLicenseBadges = true,
    )
}
