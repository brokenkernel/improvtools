package com.brokenkernel.improvtools.application.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

/**
 * Allows the caller to specify that some specific lambda should occur on each recompose
 */
@Composable
fun LaunchWrapper(
    onLaunchCallback: () -> Unit,
    content: @Composable () -> Unit,
) {
    LaunchedEffect(onLaunchCallback) {
        onLaunchCallback()
    }
    content()
}
