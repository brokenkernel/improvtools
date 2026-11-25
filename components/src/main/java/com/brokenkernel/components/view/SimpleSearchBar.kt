package com.brokenkernel.components.view

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.ExpandedDockedSearchBar
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SearchBarState
import androidx.compose.material3.SearchBarValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import com.brokenkernel.components.R
import kotlinx.coroutines.launch

@JvmSynthetic
@PublishedApi
@Composable
internal fun BoxScope.SimpleSearchBar(
    textFieldState: TextFieldState,
    modifier: Modifier = Modifier,
    trailingIcon: @Composable (() -> Unit)? = null,
    searchBarState: SearchBarState = rememberSearchBarState(
        initialValue = SearchBarValue.Expanded,
    ),
    content: @Composable () -> Unit,
) {
    val scope = rememberCoroutineScope()

    val inputField = @Composable {
        SearchBarDefaults.InputField(
            textFieldState = textFieldState,
            searchBarState = searchBarState,
            onSearch = {
                scope.launch {
                    searchBarState.animateToExpanded()
                }
            },
            placeholder = {
                Text(
                    modifier = Modifier.clearAndSetSemantics {},
                    text = stringResource(R.string.search_placeholder),
                )
            },
            leadingIcon = {
                Icon(
                    ImageVector.vectorResource(R.drawable.search_24dp_1f1f1f_fill0_wght400_grad0_opsz24),
                    contentDescription = stringResource(R.string.simple_search_bar_search),
                )
            },
            trailingIcon = trailingIcon,
        )
    }
    Column(modifier = modifier) {
        SearchBar(
            state = searchBarState,
            inputField = inputField,
            modifier = Modifier
                .semantics { traversalIndex = 0f },
        )

        ExpandedDockedSearchBar(
            state = searchBarState,
            inputField = inputField,
        ) {
            content()
        }
    }
}
