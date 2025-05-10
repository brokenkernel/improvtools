package com.brokenkernel.improvtools.encyclopaedia.presentation.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.brokenkernel.improvtools.components.sidecar.preview.ImprovToolsAllPreviews
import com.brokenkernel.improvtools.encyclopaedia.presentation.viewmodel.LoadableSingleWordThesaurusButtonViewModel

@Composable
fun LoadableSingleWordThesaurusButton(
    word: String,
    onNavigateToWord: (String) -> Unit,
    modifier: Modifier = Modifier,
    whenDisabledFullyHidden: Boolean = false,
    viewModel: LoadableSingleWordThesaurusButtonViewModel = hiltViewModel(),
) {
    val doesHaveDictionaryDetails: Boolean by viewModel.doesHaveDictionaryDetails(word)
        .collectAsStateWithLifecycle()

    if (doesHaveDictionaryDetails || !whenDisabledFullyHidden) {
        SingleWordThesaurusButton(
            word = word,
            onNavigateToWord = onNavigateToWord,
            enabled = doesHaveDictionaryDetails,
            modifier = modifier,
        )
    }
}

@ImprovToolsAllPreviews
@Composable
private fun ExampleLoadableSingleWordThesaurusButtonPreview() {
    LoadableSingleWordThesaurusButton(
        word = "access",
        onNavigateToWord = {},
    )
}
