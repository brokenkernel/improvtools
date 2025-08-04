package com.brokenkernel.improvtools.tonguetwister.view

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.brokenkernel.components.view.HtmlText
import com.brokenkernel.components.view.PageDots
import com.brokenkernel.improvtools.tonguetwister.data.TongueTwisterDatum
import com.brokenkernel.improvtools.tonguetwister.data.TongueTwisterItem
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.ExternalModuleGraph
import com.ramcosta.composedestinations.annotation.parameters.CodeGenVisibility
import kotlinx.collections.immutable.ImmutableList

@Composable
internal fun SingleTongueTwisterCard(
    ttitem: TongueTwisterItem,
) {
    Card(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxSize(),
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
        ) {
            SelectionContainer {
                HtmlText(ttitem.text)
            }
        }
        val explanation = ttitem.explanation
        if (explanation != null) {
            val scrollState: ScrollState = rememberScrollState()
            Row(modifier = Modifier.verticalScroll(scrollState)) {
                SelectionContainer {
                    HtmlText(ttitem.explanation)
                }
            }
        }
    }
}

@Composable
internal fun OuterTongueTwisterOutline(
    datum: ImmutableList<TongueTwisterItem>,
    modifier: Modifier = Modifier,
) {
    val pagerState = rememberPagerState(
        initialPage = 0, // figure out way to save this in viewmodel or similar(?). Like T&T
        pageCount = { datum.size },
    )
    Column(modifier = modifier) {
        HorizontalPager(
            pagerState,
            contentPadding = PaddingValues(2.dp),
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
        ) {
            SingleTongueTwisterCard(datum[pagerState.currentPage])
        }
        PageDots(
            pagerState.currentPage,
            pagerState.pageCount,
        )
    }
}

@Destination<ExternalModuleGraph>(
    start = true,
    visibility = CodeGenVisibility.PUBLIC,
)
@Composable
internal fun TongueTwisterTab() {
    // consider some way to save state for which item we're up to.
    // same concern as TipsAndTricks...
    OuterTongueTwisterOutline(
        TongueTwisterDatum,
    )
}
