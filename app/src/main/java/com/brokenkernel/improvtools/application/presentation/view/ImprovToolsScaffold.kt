package com.brokenkernel.improvtools.application.presentation.view

import androidx.annotation.UiThread
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.application.ApplicationConstants.APPLICATION_TITLE
import com.brokenkernel.improvtools.application.data.model.ImprovToolsAppState
import com.brokenkernel.improvtools.application.presentation.api.BottomSheetContent
import com.brokenkernel.improvtools.application.presentation.api.LocalBottomSheetContentManager
import com.brokenkernel.improvtools.application.presentation.api.LocalSnackbarHostState
import com.brokenkernel.improvtools.sidecar.customtabs.CustomTabUriHandler

// this should be handled by navigation, but for now, it works
/**
 * Maintains a method which itself sets bottom sheet content.
 * Useful to be a method since it can also possibly _show_ the bottom sheet.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ImprovToolsScaffold(
    improvToolsAppState: ImprovToolsAppState,
    navMenuButtonPressedCallback: () -> Unit,
    content: @Composable (() -> Unit),
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val customTabHandler = CustomTabUriHandler(LocalContext.current)
    val sheetState = rememberModalBottomSheetState()
    // TODO: this should be navigation based, but meh, future work
    val bottomSheetContent =  improvToolsAppState.bottomSheetContent

    @UiThread
    fun setAndShowBottomContent(
        content: BottomSheetContent?,
    ) {
        improvToolsAppState.setBottomSheetTo(content)
    }

    CompositionLocalProvider(
        values = arrayOf(
            LocalSnackbarHostState provides snackbarHostState,
            LocalUriHandler provides customTabHandler,
            LocalBottomSheetContentManager provides ::setAndShowBottomContent,
        ),
    ) {
        // TODO: replace with [[NavigationSuiteScaffold]]
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
                if (bottomSheetContent != null) {
                    ModalBottomSheet(
                        onDismissRequest = {
                            improvToolsAppState.setBottomSheetTo(null)
                        },
                        sheetState = sheetState,
                    ) {
                        Column {
                            bottomSheetContent()
                        }
                    }
                }
            }
        }
    }
}
