package com.brokenkernel.improvtools.coreinfra

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

/**
 * Allows the caller to specify that some specific lambda should occur on each recompose
 */
@Composable
public fun LaunchWrapper(
    onLaunchCallback: () -> Unit,
    content: @Composable () -> Unit,
) {
    LaunchedEffect(onLaunchCallback) {
        onLaunchCallback()
    }
    content()
}
