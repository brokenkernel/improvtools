package com.brokenkernel.improvtools.application.presentation.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.mikepenz.aboutlibraries.ui.compose.m3.LibrariesContainer

@Composable
internal fun LibrariesTab(onLaunchTitleCallback: () -> Unit) {
    // TODO: consider making a BaseScreenComposable or some such
    LaunchedEffect(Unit) {
        onLaunchTitleCallback()
    }

    LibrariesContainer(
        modifier = Modifier.fillMaxSize(),
    )
}
