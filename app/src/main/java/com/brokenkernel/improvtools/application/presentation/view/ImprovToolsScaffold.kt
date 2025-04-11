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
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScope
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.application.data.model.NavigableScreens
import com.brokenkernel.improvtools.settings.presentation.view.SuggestionsScreenMenu
import com.brokenkernel.improvtools.settings.presentation.view.TipsAndAdviceMenu

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ImprovToolsScaffold(
    currentNavigableScreen: State<NavigableScreens>,
    doNavigateToNavigableScreen: (NavigableScreens) -> Unit,
    navMenuButtonPressedCallback: () -> Unit,
    content: @Composable (() -> Unit),
) {
    val snackbarHostState = remember { SnackbarHostState() }
    var moreMenuExpandedState: Boolean by remember { mutableStateOf(false) }

    fun NavigationSuiteScope.simpleNavigableScreen(it: NavigableScreens) {
        return item(
            icon = {
                Icon(
                    it.icon,
                    contentDescription = stringResource(it.contentDescription)
                )
            },
            label = {
                Text(stringResource(it.titleResource))
            },
            selected = (currentNavigableScreen.value == it),
            onClick = {
                doNavigateToNavigableScreen(it)
            }
        )
    }


    NavigationSuiteScaffold(
        navigationSuiteItems = {
            simpleNavigableScreen(NavigableScreens.SuggestionGenerator)
            simpleNavigableScreen(NavigableScreens.Timer)
            simpleNavigableScreen(NavigableScreens.Encyclopaedia)
        },
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = { Text(stringResource(currentNavigableScreen.value.titleResource)) },
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
                            onClick = {
                                moreMenuExpandedState = !moreMenuExpandedState
                            },
                            enabled = currentNavigableScreen.value.shouldShowExtraMenu,
                        ) {
                            if (currentNavigableScreen.value.shouldShowExtraMenu) {
                                Icon(
                                    imageVector = Icons.Filled.MoreVert,
                                    contentDescription = stringResource(R.string.navigation_open_screen_specific_menu)
                                )
                                when (currentNavigableScreen.value) {
                                    NavigableScreens.SuggestionGenerator -> SuggestionsScreenMenu(
                                        expanded = moreMenuExpandedState,
                                        onDismiss = {
                                            moreMenuExpandedState = !moreMenuExpandedState
                                        }
                                    )

                                    NavigableScreens.TipsAndAdvice -> TipsAndAdviceMenu(
                                        expanded = moreMenuExpandedState,
                                        onDismiss = {
                                            moreMenuExpandedState = !moreMenuExpandedState
                                        }
                                    )

                                    else -> {
                                        // There is no menu, so we're good.
                                    }
                                }
                            }

                        }
                    },
                )
            },
//            bottomBar = {
//                ImprovToolsBottomBar(currentNavigableScreen, doNavigateToNavigableScreen)
//            },
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            },
        ) { innerPadding ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                content = content,
            )
        }
    }

}