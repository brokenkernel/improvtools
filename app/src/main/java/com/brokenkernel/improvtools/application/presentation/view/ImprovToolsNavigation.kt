package com.brokenkernel.improvtools.application.presentation.view

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.brokenkernel.improvtools.application.data.model.NavigableActivities
import kotlinx.coroutines.launch


@Composable
fun ImprovToolsNavigationDrawer(content: @Composable () -> Unit) {
    // TODO: move this to a UIState or some such
    var selectedItemIndex by rememberSaveable { mutableStateOf(0) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                NavigableActivities.entries.forEachIndexed { index, item ->
                    NavigationDrawerItem(
                        label = { Text(text= stringResource(item.titleResource)) },
                        onClick = {
                            // TODO - launch activity or something here
                            selectedItemIndex = index
                            scope.launch {
                                drawerState.close() // TODO: close on launch
                            }
                        },

                        selected = (index == selectedItemIndex),
                    )
                }
            }
        },
        content = content
    )
}
