package com.brokenkernel.improvtools.encyclopaedia.presentation.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.brokenkernel.improvtools.components.sidecar.preview.ImprovToolsAllPreviews
import com.brokenkernel.improvtools.encyclopaedia.presentation.viewmodel.LoadableSingleWordThesaurusButtonViewModel

@Composable
fun LoadableSingleWordThesaurusButton(
    viewModel: LoadableSingleWordThesaurusButtonViewModel,
    onNavigateToWord: (String) -> Unit,
) {
    val doesHaveDictionaryDetails by viewModel.doesHaveDictionaryDetails.collectAsStateWithLifecycle()

    SingleWordThesaurusButton(
        word = viewModel.word,
        onNavigateToWord = onNavigateToWord,
        enabled = doesHaveDictionaryDetails,
    )
}

@ImprovToolsAllPreviews
@Composable
fun ExampleLoadableSingleWordThesaurusButtonPreview() {
    val viewModel =
        hiltViewModel<
            LoadableSingleWordThesaurusButtonViewModel,
            LoadableSingleWordThesaurusButtonViewModel.Factory,
            >(
            key = "access",
            creationCallback = { factory ->
                factory.create("access")
            },
        )
    LoadableSingleWordThesaurusButton(
        viewModel = viewModel,
        onNavigateToWord = {},
    )
}
