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
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScope
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.application.data.model.NavigableRoute
import com.brokenkernel.improvtools.application.data.model.NavigableScreens
import com.brokenkernel.improvtools.application.data.model.routeToScreen
import com.brokenkernel.improvtools.settings.presentation.view.SuggestionsScreenMenu
import com.brokenkernel.improvtools.settings.presentation.view.TipsAndAdviceMenu


internal fun wrongfullyFindRouteByNavDestination(dest: NavDestination?): NavigableRoute {
    NavigableRoute::class.sealedSubclasses.forEach {
        if (dest?.hasRoute(it) == true) {
            return it.objectInstance!!
        }
    }
    // should never happyn
    return NavigableRoute.SuggestionGeneratorRoute
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ImprovToolsScaffold(
    currentBackStackEntry: NavBackStackEntry?,
    doNavigateToNavigableRoute: (NavigableRoute) -> Unit,
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
            selected = (currentBackStackEntry?.destination?.hasRoute(it.route::class) == true),
            onClick = {
                doNavigateToNavigableRoute(it.route)
            }
        )
    }

    val currentNavigableRoute = wrongfullyFindRouteByNavDestination(currentBackStackEntry?.destination)


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = { Text(stringResource(routeToScreen(currentNavigableRoute).titleResource)) },
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
                        enabled = routeToScreen(currentNavigableRoute).shouldShowExtraMenu,
                    ) {
                        if (routeToScreen(currentNavigableRoute).shouldShowExtraMenu) {
                            Icon(
                                imageVector = Icons.Filled.MoreVert,
                                contentDescription = stringResource(R.string.navigation_open_screen_specific_menu)
                            )
                            when (routeToScreen(currentNavigableRoute)) {
                                NavigableScreens.SuggestionGeneratorScreen -> SuggestionsScreenMenu(
                                    expanded = moreMenuExpandedState,
                                    onDismiss = {
                                        moreMenuExpandedState = !moreMenuExpandedState
                                    }
                                )

                                NavigableScreens.TipsAndAdviceScreen -> TipsAndAdviceMenu(
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