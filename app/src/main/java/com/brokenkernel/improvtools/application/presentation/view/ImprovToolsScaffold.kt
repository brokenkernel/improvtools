package com.brokenkernel.improvtools.application.presentation.view

import androidx.annotation.UiThread
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.launch

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
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Hidden,
            skipHiddenState = false,
        ),
    )
//    val windowInfo = LocalWindowInfo.current
//    val screenHeight = windowInfo.containerSize.height.dp
//    val peekHeight = (screenHeight / 4)

    val scope = rememberCoroutineScope()
    // TODO: this should be navigation based, but meh, future work
    val bottomSheetContent = improvToolsAppState.bottomSheetContent

    @UiThread
    fun setAndShowBottomContent(
        content: BottomSheetContent = {},
    ) {
        improvToolsAppState.bottomSheetContent = content
        scope.launch {
            bottomSheetScaffoldState.bottomSheetState.partialExpand()
        }
    }

    CompositionLocalProvider(
        values = arrayOf(
            LocalSnackbarHostState provides snackbarHostState,
            LocalUriHandler provides customTabHandler,
            LocalBottomSheetContentManager provides ::setAndShowBottomContent,
        ),
    ) {
        // TODO: replace with [[NavigationSuiteScaffold]]
        BottomSheetScaffold(
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
            sheetContent = bottomSheetContent,
//            sheetPeekHeight = peekHeight,
            scaffoldState = bottomSheetScaffoldState,
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
