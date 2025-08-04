package com.brokenkernel.improvtools.encyclopaedia.android.tipsandadvice.view

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.brokenkernel.components.view.HtmlText
import com.brokenkernel.components.view.PageDots
import com.brokenkernel.improvtools.encyclopaedia.data.tipsandadvice.TipsAndAdviceUIState

@Composable
internal fun TipsAndAdviceScreenAsSwipable(
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
        PageDots(
            pagerState.currentPage,
            pagerState.pageCount,
        )
    }
}
