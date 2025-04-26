package com.brokenkernel.improvtools.encyclopaedia.presentation.view

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.application.presentation.view.verticalColumnScrollbar
import com.brokenkernel.improvtools.components.presentation.view.HtmlText
import com.brokenkernel.improvtools.encyclopaedia.data.model.TipContentUIModel
import com.brokenkernel.improvtools.encyclopaedia.data.model.TipsAndAdviceViewModeUI
import com.brokenkernel.improvtools.encyclopaedia.presentation.viewmodel.TipsAndAdviceViewModel

@Composable
internal fun TipsAndAdviceScreenAsSwipable(viewModel: TipsAndAdviceViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scrollState: ScrollState = rememberScrollState()
    val pagerState = rememberPagerState(
        initialPage = 1, // TODO: maybe remember this in settings? (maybe split 'remembered data' from 'settings'?)
        pageCount = { uiState.tipsAndAdvice.size },
    )
    Column {
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

@Composable
internal fun TipsAndAdviceScreenAsList(viewModel: TipsAndAdviceViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val columnScrollState: ScrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .verticalColumnScrollbar(
                columnScrollState,
            )
            .verticalScroll(columnScrollState),
    ) {
        uiState.tipsAndAdvice.forEach { it: TipContentUIModel ->
            val isExpanded = remember { mutableStateOf(false) }
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                ),
                onClick = {
                    isExpanded.value = !isExpanded.value
                },
            ) {
                Row {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 8.dp), // TODO: from resource?
                    ) {
                        Row {
                            SelectionContainer {
                                Text(
                                    text = it.title,
                                )
                            }
                            // force arrow to the end
                            Spacer(
                                Modifier
                                    .weight(1f)
                                    .height(0.dp),
                            )
                            if (isExpanded.value) {
                                Icon(
                                    Icons.Default.ArrowUpward,
                                    contentDescription = stringResource(
                                        R.string.tips_and_advice_collapse_card,
                                    ),
                                )
                            } else {
                                Icon(
                                    Icons.Default.ArrowDownward,
                                    contentDescription = stringResource(
                                        R.string.tips_and_advice_expand_card,
                                    ),
                                )
                            }
                        }
                    }
                }
                if (isExpanded.value) {
                    Row {
                        SelectionContainer {
                            HtmlText(it.content)
                        }
                    }
                }
            }
            Spacer(Modifier.size(1.dp)) // TODO: move to resources?
        }
    }
}

@Composable
internal fun TipsAndAdviceTab(
    viewModel: TipsAndAdviceViewModel = hiltViewModel(),
    onLaunchTitleCallback: () -> Unit,
) {
    // TODO: consider making a BaseScreenComposable or some such
    LaunchedEffect(Unit) {
        onLaunchTitleCallback()
    }

    val taaViewMode by viewModel.taaViewMode.collectAsStateWithLifecycle()
    when (taaViewMode) {
        TipsAndAdviceViewModeUI.SWIPEABLE -> TipsAndAdviceScreenAsSwipable()
        TipsAndAdviceViewModeUI.LIST -> TipsAndAdviceScreenAsList()
    }
}
