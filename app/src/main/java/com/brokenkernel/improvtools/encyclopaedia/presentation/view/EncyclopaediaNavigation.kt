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
import com.brokenkernel.improvtools.application.data.model.NavigableScreens

internal enum class EncyclopaediaPages(
    @param:StringRes val title: Int,
    val icon: ImageVector,
    val navigableScreen: NavigableScreens,
    val content: @Composable () -> Unit,
) {
    GamesPage(
        R.string.encyclopaedia_tab_title_games,
        Icons.Outlined.Games,
        NavigableScreens.GamesPage,
        { GamesTab() },
    ),
    PeoplePage(
        R.string.encyclopaedia_tab_title_people,
        Icons.Outlined.People,
        NavigableScreens.PeoplePage,
        { PeopleTab() },
    ),
    EmotionsPage(
        R.string.encyclopaedia_tab_title_emotions,
        Icons.Outlined.EmojiEmotions,
        NavigableScreens.EmotionsPage,
        { EmotionTab() },
    ),
    ThesaurusPage(
        R.string.encyclopaedia_tab_title_thesaurus,
        Icons.Filled.Book,
        NavigableScreens.ThesaurusPage,
        { ThesaurusTab() },
    ),
}

internal fun NavGraphBuilder.encyclopaediaPageDestinations(onNavigateToScreen: (NavigableScreens) -> Unit) {
    composable<NavigableRoute.GamesPageRoute> {
        EncyclopaediaScreen(onNavigateToScreen = onNavigateToScreen, initialTab = EncyclopaediaPages.GamesPage)
    }

    composable<NavigableRoute.PeoplePageRoute> {
        EncyclopaediaScreen(onNavigateToScreen = onNavigateToScreen, initialTab = EncyclopaediaPages.PeoplePage)
    }

    composable<NavigableRoute.EmotionPageRoute> {
        EncyclopaediaScreen(onNavigateToScreen = onNavigateToScreen, initialTab = EncyclopaediaPages.EmotionsPage)
    }

    composable<NavigableRoute.ThesaurusPageRoute> {
        EncyclopaediaScreen(onNavigateToScreen = onNavigateToScreen, initialTab = EncyclopaediaPages.ThesaurusPage)
    }
}
