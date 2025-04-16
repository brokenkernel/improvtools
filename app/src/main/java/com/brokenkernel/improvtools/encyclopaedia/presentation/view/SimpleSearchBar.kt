package com.brokenkernel.improvtools.encyclopaedia.presentation.view

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.unit.dp
import com.brokenkernel.improvtools.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BoxScope.SimpleSearchBar(
    textFieldState: TextFieldState,
    content: @Composable () -> Unit
) {
    var searchBarExpandedState by rememberSaveable { mutableStateOf(false) }

    SearchBar(
        inputField = {
            SearchBarDefaults.InputField(
                query = textFieldState.text.toString(),
                onQueryChange = { it ->
                    textFieldState.edit { replace(0, length, it) }
                },
                onSearch = {
                    searchBarExpandedState = false
                },
                expanded = searchBarExpandedState,
                onExpandedChange = { searchBarExpandedState != searchBarExpandedState },
                placeholder = { Text("Search") },
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = stringResource(R.string.search_bar_search)
                    )
                },

//                    trailingIcon = {
//                        Icon(
//                            Icons.Default.UnfoldMoreDouble,
//                            contentDescription = "Search"
//                        )
//                    },
            )
        },
        expanded = true,
        onExpandedChange = { it -> searchBarExpandedState = it },
        modifier = Modifier
            .align(Alignment.TopCenter)
            .semantics { traversalIndex = 0f },
        windowInsets = WindowInsets(top = 0.dp),
    ) {
        content()
    }
}