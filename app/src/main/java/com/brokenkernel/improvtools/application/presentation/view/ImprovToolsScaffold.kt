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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.application.ApplicationConstants.APPLICATION_TITLE
import com.brokenkernel.improvtools.application.data.model.ImprovToolsAppState
import com.brokenkernel.improvtools.sidecar.customtabs.CustomTabUriHandler

internal val LocalSnackbarHostState: ProvidableCompositionLocal<SnackbarHostState> =
    compositionLocalOf<SnackbarHostState> {
        error("No Snackbar Host State")
    }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ImprovToolsScaffold(
    improvToolsAppState: ImprovToolsAppState,
    navMenuButtonPressedCallback: () -> Unit,
    content: @Composable (() -> Unit),
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val customTabHandler = CustomTabUriHandler(LocalContext.current)

    CompositionLocalProvider(
        values = arrayOf(
            LocalSnackbarHostState provides snackbarHostState,
            LocalUriHandler provides customTabHandler,
        ),
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Text(
                            stringResource(improvToolsAppState.currentTitle.value),
                            modifier = Modifier.testTag(APPLICATION_TITLE),
                        )
                    },
                    scrollBehavior = TopAppBarDefaults
                        .exitUntilCollapsedScrollBehavior(
                            rememberTopAppBarState(),
                        ),
                    navigationIcon = {
                        IconButton(onClick = navMenuButtonPressedCallback) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = stringResource(R.string.navigation_app_menu),
                            )
                        }
                    },
                    actions = {
                        val curMenu = improvToolsAppState.extraMenu.value
                        if (curMenu != null) {
                            IconButton(
                                onClick = {
                                    improvToolsAppState.extraMenuExpandedState =
                                        !improvToolsAppState.extraMenuExpandedState
                                },
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.MoreVert,
                                    contentDescription = stringResource(
                                        R.string.navigation_open_screen_specific_menu,
                                    ),
                                )
                                curMenu()
                            }
                        }
                    },
                )
            },
            snackbarHost = {
                SnackbarHost(hostState = LocalSnackbarHostState.current)
            },
        ) { innerPadding ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            ) {
                content()
            }
        }
    }
}
