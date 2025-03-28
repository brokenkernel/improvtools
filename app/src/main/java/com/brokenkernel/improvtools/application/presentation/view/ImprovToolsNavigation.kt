package com.brokenkernel.improvtools.application.presentation.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.application.data.model.NavigableActivities
import com.brokenkernel.improvtools.application.presentation.uistate.ApplicationNavigationState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun ImprovToolsNavigationDrawer(screenTitle: String, content: @Composable () -> Unit) {
    // TODO: move this to a UIState or some such
    var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }

    val drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed) // TODO move out
    val anState = ApplicationNavigationState(drawerState)
    val scope: CoroutineScope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = anState.drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(stringResource(R.string.app_name), modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleLarge)
                    HorizontalDivider()
                    Text(stringResource(R.string.navigation_useful_tools_category), modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleMedium)
                    NavigableActivities.entries.forEachIndexed { index, item ->
                        NavigationDrawerItem(
                            label = { Text(text = stringResource(item.titleResource)) },
                            onClick = {
                                // TODO - launch activity or something here
                                selectedItemIndex = index
                                scope.launch {
                                    anState.drawerState.close() // TODO: close on launch
                                }
                            },

                            selected = (index == selectedItemIndex),
                        )
                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                        Text(stringResource(R.string.navigation_settings_category), modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleMedium)
                        NavigationDrawerItem(
                            label = { Text(stringResource(R.string.navigation_settings)) },
                            selected = false,
                            icon = { Icon(Icons.Outlined.Settings, contentDescription = stringResource(R.string.navigation_settings)) },
                            onClick = {}
                        )
                        NavigationDrawerItem(
                            label = { Text(stringResource(R.string.navigation_help_and_feedback)) },
                            selected = false,
                            icon = { Icon(Icons.Outlined.Info, contentDescription = stringResource(R.string.navigation_help_and_feedback)) },
                            onClick = { },
                        )
                        Spacer(Modifier.height(12.dp))
                    }
                }
            }
        },
        content = {
            ImprovToolsScaffold(screenTitle = screenTitle,
                content = content,
                menuScope = scope,
                drawerState = anState.drawerState)
        }
    )
}