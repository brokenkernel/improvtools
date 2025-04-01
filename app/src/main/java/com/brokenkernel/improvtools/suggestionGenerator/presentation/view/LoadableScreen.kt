package com.brokenkernel.improvtools.suggestionGenerator.presentation.view

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.brokenkernel.improvtools.suggestionGenerator.presentation.viewmodel.LoadableScreenViewModel

@Composable
fun LoadableScreen(
    loader: () -> Unit,
    viewModel: LoadableScreenViewModel = viewModel(factory = LoadableScreenViewModel.Factory(loader)),
    content: @Composable () -> Unit,
) {
    val isLoading by viewModel.isLoading.collectAsState()
    if (isLoading) {
        CircularProgressIndicator()
    } else {
        content()
    }
}