package com.brokenkernel.improvtools.application.presentation.view

import androidx.annotation.UiThread
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.brokenkernel.components.view.SimpleIconButton
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.application.ApplicationConstants.APPLICATION_TITLE
import com.brokenkernel.improvtools.application.data.model.ImprovToolsAppState
import com.brokenkernel.improvtools.coreinfra.BottomSheetContent
import com.brokenkernel.improvtools.coreinfra.LocalBottomSheetContentManager
import com.brokenkernel.improvtools.coreinfra.LocalSnackbarHostState
import com.brokenkernel.improvtools.sidecar.customtabs.CustomTabUriHandler

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ImprovToolsScaffold(
    improvToolsAppState: ImprovToolsAppState,
    navMenuButtonPressedCallback: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable (() -> Unit),
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val customTabHandler = CustomTabUriHandler(LocalContext.current)
    val sheetState = rememberModalBottomSheetState()

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
        // TODO: this should be navigation based, but meh, future work
        val bottomSheetContent = improvToolsAppState.bottomSheetContent.collectAsStateWithLifecycle()
        val extraMenu = improvToolsAppState.extraMenu.collectAsStateWithLifecycle()
        val currentTitle = improvToolsAppState.currentTitle.collectAsStateWithLifecycle()
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
                            stringResource(currentTitle.value),
                            modifier = Modifier.testTag(APPLICATION_TITLE),
                        )
                    },
                    scrollBehavior = TopAppBarDefaults
                        .exitUntilCollapsedScrollBehavior(
                            rememberTopAppBarState(),
                        ),
                    navigationIcon = {
                        SimpleIconButton(
                            onClick = navMenuButtonPressedCallback,
                            // TODO: switch to MenuOpen when required
                            icon = ImageVector.vectorResource(R.drawable.menu_24dp_1f1f1f_fill0_wght400_grad0_opsz24),
                            contentDescription = stringResource(R.string.navigation_app_menu),
                        )
                    },
                    actions = {
                        if (extraMenu.value != null) {
                            SimpleIconButton(
                                onClick = {
                                    improvToolsAppState.extraMenuExpandedState =
                                        !improvToolsAppState.extraMenuExpandedState
                                },
                                icon = ImageVector.vectorResource(
                                    R.drawable.more_vert_24dp_1f1f1f_fill0_wght400_grad0_opsz24,
                                ),
                                contentDescription = stringResource(
                                    R.string.navigation_open_screen_specific_menu,
                                ),
                            )
                        }
                        extraMenu.value?.invoke(improvToolsAppState)
                    },
                )
            },
            snackbarHost = {
                SnackbarHost(hostState = LocalSnackbarHostState.current)
            },
            modifier = modifier,
        ) { innerPadding ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .consumeWindowInsets(innerPadding),
            ) {
                content()
                // this should be handled by navigation, but for now, it works
                if (bottomSheetContent.value != null) {
                    ModalBottomSheet(
                        onDismissRequest = {
                            improvToolsAppState.setBottomSheetTo(null)
                        },
                        sheetState = sheetState,
                    ) {
                        Column {
                            bottomSheetContent.value?.invoke(this)
                        }
                    }
                }
            }
        }
    }
}
