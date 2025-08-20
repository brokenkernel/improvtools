package com.brokenkernel.components.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.brokenkernel.components.viewmodel.LoadableScreenViewModel

@Composable
public fun LoadableScreen(
    loader: () -> Unit,
    preloadedContent: @Composable () -> Unit,
    viewModel: LoadableScreenViewModel =
        viewModel(factory = LoadableScreenViewModel.Factory(loader)),
    loadedContent: @Composable () -> Unit,
) {
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    if (isLoading) {
        preloadedContent()
    } else {
        loadedContent()
    }
}
