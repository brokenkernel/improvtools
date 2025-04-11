package com.brokenkernel.improvtools.encyclopaedia.presentation.view

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.outlined.EmojiEmotions
import androidx.compose.material.icons.outlined.Games
import androidx.compose.material.icons.outlined.People
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.application.data.model.NavigableRoute
import com.brokenkernel.improvtools.application.data.model.NavigableScreens
import com.brokenkernel.improvtools.application.data.model.rememberImprovToolsAppState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EncyclopaediaScreen() {
    val pagerState = rememberPagerState(pageCount = { EncyclopaediaPages.entries.size }, initialPage = 1)
    val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }
    val appState = rememberImprovToolsAppState()

    LaunchedEffect(pagerState) {
        appState.navigateTo(EncyclopaediaPages.entries[pagerState.currentPage].navigableScreen)
    }


    Column(modifier = Modifier.fillMaxSize()) {
        SecondaryTabRow(
            selectedTabIndex = selectedTabIndex.value,
        ) {
            val scope = rememberCoroutineScope()
            EncyclopaediaPages.entries.forEachIndexed { idx, page ->
                Tab(
                    text = { Text(stringResource(page.title)) },
                    selected = selectedTabIndex.value == idx,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(idx)
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = page.icon,
                            contentDescription = stringResource(page.title),
                        )
                    },
                )
            }
        }
        /* TODO: maybe handle some idea of sub-routing. or Add another nav graph. This should allow navigating to
        *   a specific tab. Also keeps the entire screen state in a graph. Also makes analytics proper for crash debugging. */
        HorizontalPager(state = pagerState) {
            Surface(modifier = Modifier.fillMaxSize()) {
                EncyclopaediaPages.entries[pagerState.currentPage].content()
            }
        }
    }
}

@Preview
@Composable
fun PreviewEncyclopaediaScreen() {
    EncyclopaediaScreen()
}
