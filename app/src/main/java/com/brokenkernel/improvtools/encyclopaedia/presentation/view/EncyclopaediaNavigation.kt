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
    val navigableRoute: NavigableRoute,
    val content: @Composable () -> Unit,
) {
    GamesPage(
        R.string.encyclopaedia_tab_title_games,
        Icons.Outlined.Games,
        NavigableRoute.GamesPageRoute,
        { GamesTab() },
    ),
    PeoplePage(
        R.string.encyclopaedia_tab_title_people,
        Icons.Outlined.People,
        NavigableRoute.PeoplePageRoute,
        { PeopleTab() },
    ),
    EmotionsPage(
        R.string.encyclopaedia_tab_title_emotions,
        Icons.Outlined.EmojiEmotions,
        NavigableRoute.EmotionPageRoute,
        { EmotionTab() },
    ),
    ThesaurusPage(
        R.string.encyclopaedia_tab_title_thesaurus,
        Icons.Filled.Book,
        NavigableRoute.ThesaurusPageRoute,
        { ThesaurusTab() },
    ),
}

internal fun NavGraphBuilder.encyclopaediaPageDestinations(onNavigateToRoute: (NavigableRoute) -> Unit) {
    composable<NavigableRoute.GamesPageRoute> {
        EncyclopaediaScreen(onNavigateToRoute = onNavigateToRoute, initialTab = EncyclopaediaPages.GamesPage)
    }

    composable<NavigableRoute.PeoplePageRoute> {
        EncyclopaediaScreen(onNavigateToRoute = onNavigateToRoute, initialTab = EncyclopaediaPages.PeoplePage)
    }

    composable<NavigableRoute.EmotionPageRoute> {
        EncyclopaediaScreen(onNavigateToRoute = onNavigateToRoute, initialTab = EncyclopaediaPages.EmotionsPage)
    }

    composable<NavigableRoute.ThesaurusPageRoute> {
        EncyclopaediaScreen(onNavigateToRoute = onNavigateToRoute, initialTab = EncyclopaediaPages.ThesaurusPage)
    }
}
