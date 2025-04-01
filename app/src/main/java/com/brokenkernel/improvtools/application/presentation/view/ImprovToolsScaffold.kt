package com.brokenkernel.improvtools.application.presentation.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.application.data.model.NavigableScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ImprovToolsScaffold(
    content: @Composable () -> Unit,
    currentNavigableScreen: NavigableScreens,
    doNavigateToNavigableScreen: (NavigableScreens) -> Unit,
    navMenuButtonPressedCallback: () -> Unit,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = { Text(stringResource(currentNavigableScreen.titleResource)) },
                scrollBehavior = TopAppBarDefaults
                    .exitUntilCollapsedScrollBehavior(
                        rememberTopAppBarState()
                    ),
                navigationIcon = {
                    IconButton(onClick = navMenuButtonPressedCallback) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = stringResource(R.string.navigation_app_menu)
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {},
                        // possibly use this for visible/invisible rather than enabled/disabled
                        enabled = currentNavigableScreen.shouldShowExtraMenu,
                    ) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = stringResource(R.string.navigation_open_screen_specific_menu)
                        )
                    }
                },
            )
        },
        bottomBar = {
            ImprovToolsBottomBar(currentNavigableScreen, doNavigateToNavigableScreen)
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            content = content
        )
    }
}