package com.brokenkernel.improvtools.application.presentation.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier

/**
 * Allows the caller to specify that some specific lambda should occur on each recompose
 */
@Composable
fun LaunchWrapper(
    onLaunchCallback: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    LaunchedEffect(onLaunchCallback) {
        onLaunchCallback()
    }
    content()
}
