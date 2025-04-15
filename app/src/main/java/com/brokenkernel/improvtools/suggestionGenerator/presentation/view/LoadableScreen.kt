package com.brokenkernel.improvtools.suggestionGenerator.presentation.view

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.brokenkernel.improvtools.suggestionGenerator.presentation.viewmodel.LoadableScreenViewModel

@Composable
fun LoadableScreen(
    loader: () -> Unit,
    viewModel: LoadableScreenViewModel = viewModel(factory = LoadableScreenViewModel.Factory(loader)),
    content: @Composable () -> Unit,
) {
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    if (isLoading) {
        CircularProgressIndicator()
    } else {
        content()
    }
}