package com.brokenkernel.improvtools.encyclopaedia.presentation.view

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.outlined.EmojiEmotions
import androidx.compose.material.icons.outlined.Games
import androidx.compose.material.icons.outlined.People
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.application.data.model.NavigableRoute

internal enum class EncyclopaediaPages(
    @param:StringRes val title: Int,
    val icon: ImageVector,
    val content: @Composable () -> Unit,
) {
    GamesPage(
        R.string.encyclopaedia_tab_title_games,
        Icons.Outlined.Games,
        { GamesTab() },
    ),
    PeoplePage(
        R.string.encyclopaedia_tab_title_people,
        Icons.Outlined.People,
        { PeopleTab() },
    ),
    EmotionsPage(
        R.string.encyclopaedia_tab_title_emotions,
        Icons.Outlined.EmojiEmotions,
        { EmotionTab() },
    ),
    ThesaurusPage(
        R.string.encyclopaedia_tab_title_thesaurus,
        Icons.Filled.Book,
        { ThesaurusTabAllItems() },
    ),
}

internal fun NavGraphBuilder.encyclopaediaPageDestinations() {
    composable<NavigableRoute.GamesPageRoute> {
        GamesTab()
    }

    composable<NavigableRoute.PeoplePageRoute> {
        PeopleTab()
    }

    composable<NavigableRoute.EmotionPageRoute> {
        EmotionTab()
    }

    composable<NavigableRoute.ThesaurusPageRoute> {
        ThesaurusTabAllItems()
    }
}
