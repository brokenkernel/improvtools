package com.brokenkernel.improvtools.application.presentation.view

import android.util.Log
import android.webkit.ConsoleMessage.MessageLevel.LOG
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
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.application.data.model.NavigableRoute
import com.brokenkernel.improvtools.application.data.model.NavigableScreens
import com.brokenkernel.improvtools.application.data.model.allNavigableRoutes
import com.brokenkernel.improvtools.application.data.model.routeToScreen
import com.brokenkernel.improvtools.settings.presentation.view.SuggestionsScreenMenu
import com.brokenkernel.improvtools.settings.presentation.view.TipsAndAdviceMenu

private const val TAG = "ImprovToolsScaffold"

internal fun wrongfullyFindRouteByNavDestination(dest: NavDestination?, initialRoute: NavigableRoute): NavigableRoute {
    if (Log.isLoggable(TAG, Log.DEBUG)) {
        Log.d(TAG, "count of possible routes is ${allNavigableRoutes.size}")
    }

    allNavigableRoutes.forEach { possibleRoute ->
        if (Log.isLoggable(TAG, Log.DEBUG)) {
            Log.d(TAG, "checking against $possibleRoute")
        }

        if (dest?.hierarchy?.any { it ->
                it.hasRoute(possibleRoute::class)
            } == true) {
            return possibleRoute
        }
    }
    Log.wtf(TAG, "Nav destination fallback when looking for $dest")
    // should never happen
    return initialRoute
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ImprovToolsScaffold(
    currentBackStackEntry: State<NavBackStackEntry?>,
    navMenuButtonPressedCallback: () -> Unit,
    initialRoute: NavigableRoute,
    content: @Composable (() -> Unit),
) {
    val snackbarHostState = remember { SnackbarHostState() }
    var moreMenuExpandedState: Boolean by remember { mutableStateOf(false) }

    val currentNavigableRoute = wrongfullyFindRouteByNavDestination(
        currentBackStackEntry.value?.destination,
        initialRoute
    )

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
        ) {
            content()
        }
    }

}