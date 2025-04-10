package com.brokenkernel.improvtools.encyclopaedia.presentation.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.brokenkernel.improvtools.application.presentation.view.verticalColumnScrollbar

@Composable
internal fun GamesTab() {
    val scrollState = rememberScrollState()
    Column(modifier = Modifier.verticalColumnScrollbar(scrollState).verticalScroll(scrollState)) {
        Text("TODO: games list and explanation")
        Card(modifier = Modifier.fillMaxSize()) {
            Text("abc")
        }
    }

}