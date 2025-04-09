package com.brokenkernel.improvtools.encyclopaedia.presentation.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Games
import androidx.compose.material.icons.outlined.People
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
internal fun EncyclopaediaScreen() {
    var tabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Games", "People")

    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(
            selectedTabIndex = tabIndex
        ) {
            tabs.forEachIndexed { idx, title ->
                Tab(
                    text = { Text(title) },
                    selected = tabIndex == idx,
                    onClick = { tabIndex = idx },
                    icon = {
                        when(idx) {
                            0 -> Icons.Outlined.Games
                            1 -> Icons.Outlined.People
                        }
                    }
                )
            }
        }
        when (tabIndex) {
            0 -> Text("TODO: games list and explanation")
            1 -> Text("TODO: people list and explanation")
        }

    }
}

@Preview
@Composable
fun PreviewEncyclopaediaScreen() {
    EncyclopaediaScreen()
}
