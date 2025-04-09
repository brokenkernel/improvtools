package com.brokenkernel.improvtools.encyclopaedia.presentation.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.brokenkernel.improvtools.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EncyclopaediaScreen() {
    val tabs = listOf(
        stringResource(R.string.encyclopaedia_tab_title_games),
        stringResource(R.string.encyclopaedia_tab_title_people),
        stringResource(R.string.encyclopaedia_tab_title_emotions)
    )
    // start on emotions since the others are placeholders
    val pagerState = rememberPagerState(pageCount = { tabs.size }, initialPage = 2)
    val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        SecondaryTabRow(
            selectedTabIndex = selectedTabIndex.value,
        ) {
            tabs.forEachIndexed { idx, title ->
                Tab(
                    text = { Text(title) },
                    selected = selectedTabIndex.value == idx,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(idx)
                        }
                    },
                    icon = {
                        Icon(
                            imageVector =
                                // TODO: move into class of some kind to avoid this
                                when (idx) {
                                    0 -> Icons.Outlined.Games
                                    1 -> Icons.Outlined.People
                                    2 -> Icons.Outlined.EmojiEmotions
                                    else -> throw (IllegalStateException()) // this should never happen.
                                },
                            contentDescription = tabs[idx]
                        )
                    },
                )
            }
        }
        HorizontalPager(state = pagerState) { page ->
            Surface(modifier = Modifier.fillMaxSize()) {
                when (pagerState.currentPage) {
                    0 -> Text("TODO: games list and explanation")
                    1 -> Text("TODO: people list and explanation")
                    2 -> EmotionTab()
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewEncyclopaediaScreen() {
    EncyclopaediaScreen()
}
