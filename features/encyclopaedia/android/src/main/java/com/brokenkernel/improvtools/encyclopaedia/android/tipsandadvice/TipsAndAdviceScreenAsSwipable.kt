package com.brokenkernel.improvtools.encyclopaedia.android.tipsandadvice

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.brokenkernel.components.view.HtmlText
import com.brokenkernel.improvtools.encyclopaedia.data.tipsandadvice.TipsAndAdviceUIState

// TODO: make internal
@Composable
public fun TipsAndAdviceScreenAsSwipable(
    uiState: TipsAndAdviceUIState,
    modifier: Modifier = Modifier,
) {
    val scrollState: ScrollState = rememberScrollState()
    val pagerState = rememberPagerState(
        initialPage = 1, // TODO: maybe remember this in settings? (maybe split 'remembered data' from 'settings'?)
        pageCount = { uiState.tipsAndAdvice.size },
    )
    Column(modifier = modifier) {
        HorizontalPager(
            pagerState,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
        ) {
            Card(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxSize(),
            ) {
                Row {
                    SelectionContainer {
                        Text(uiState.tipsAndAdvice[pagerState.currentPage].title)
                    }
                }
                Row(modifier = Modifier.verticalScroll(scrollState)) {
                    SelectionContainer {
                        HtmlText(uiState.tipsAndAdvice[pagerState.currentPage].content)
                    }
                }
            }
        }
        Row(
            Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            repeat(pagerState.pageCount) { iteration ->
                // TODO: handle themes.
                // TODO: make reusable component
                // TODO: use a scrollbar component ?
                val color = if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(16.dp),
                )
            }
        }
    }
}
