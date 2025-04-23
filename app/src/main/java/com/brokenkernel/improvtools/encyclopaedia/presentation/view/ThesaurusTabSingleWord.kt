package com.brokenkernel.improvtools.encyclopaedia.presentation.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.application.data.model.ImprovToolsAppState
import com.brokenkernel.improvtools.application.data.model.NavigableScreens
import com.brokenkernel.improvtools.encyclopaedia.EncyclopaediaSectionNavigation
import com.brokenkernel.improvtools.encyclopaedia.presentation.viewmodel.ThesaurusSingleItemViewModel

@Composable
internal fun ThesaurusTabSingleWord(
    word: String,
    improvToolsAppState: ImprovToolsAppState,
    viewModel: ThesaurusSingleItemViewModel = hiltViewModel(),
    onLaunchTitleCallback: () -> Unit,
) {
    // TODO: consider making a BaseScreenComposable or some such
    LaunchedEffect(Unit) {
        // TODO: maybe SideEffect?
        onLaunchTitleCallback()
    }

    SelectionContainer {
        Column {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    word,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                )
            }
            viewModel.synonymsForWord(word).forEach { synonym ->
                Text(synonym)
            }
            ExtendedFloatingActionButton(
                onClick = {
                    EncyclopaediaSectionNavigation.navigateBack(improvToolsAppState)
                },
            ) {
                Text(stringResource(R.string.navigation_back_to_thesaurus))
                Icon(
                    NavigableScreens.ThesaurusPageScreen.icon,
                    contentDescription = stringResource(NavigableScreens.ThesaurusPageScreen.contentDescription),
                )
            }
        }
        // back to all words
    }
}
