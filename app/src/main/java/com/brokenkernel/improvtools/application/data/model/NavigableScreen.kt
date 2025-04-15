package com.brokenkernel.improvtools.application.data.model

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.outlined.EmojiEmotions
import androidx.compose.material.icons.outlined.Games
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material.icons.outlined.TipsAndUpdates
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.application.data.model.NavigableRoute.EmotionPageRoute
import com.brokenkernel.improvtools.application.data.model.NavigableRoute.GamesPageRoute
import com.brokenkernel.improvtools.application.data.model.NavigableRoute.HelpAndAboutRoute
import com.brokenkernel.improvtools.application.data.model.NavigableRoute.PeoplePageRoute
import com.brokenkernel.improvtools.application.data.model.NavigableRoute.SettingsRoute
import com.brokenkernel.improvtools.application.data.model.NavigableRoute.SuggestionGeneratorRoute
import com.brokenkernel.improvtools.application.data.model.NavigableRoute.ThesaurusPageRoute
import com.brokenkernel.improvtools.application.data.model.NavigableRoute.TimerRoute
import com.brokenkernel.improvtools.application.data.model.NavigableRoute.TipsAndAdviceRoute
import com.brokenkernel.improvtools.application.data.model.NavigableRoute.WorkshopGeneratorRoute
import kotlinx.serialization.Serializable

/**
 * A [NavigableRoute] is a destination that can be navigated to. It should display anique inner content.
 * If somewhere else can display a button to click to get there, it needs a route.
 *
 * This is contrast to a [NavigableScreens] which is meta-information about possible locations to visit. The same [NavigableRoute] might have multiple
 * [NavigableScreens] (for instance, the top level of a page with tabs might have an entry with different info than the entry tab).
 * It isn't required that every route have a [NavigableScreens] either.
 *
 * Put another way the [NavigableRoute] is "where to go" and [NavigableScreens] is "what am I going to"
 *
 * TODO: currently we lose all "back" state information about the [NavigableRoute] since we implement our own navigation infrastructure.
 * This should be fixed.
 * @see [NavigableScreens]
 */
@Serializable
internal sealed class NavigableRoute() {
    @Serializable
    internal object SuggestionGeneratorRoute : NavigableRoute()

    @Serializable
    internal object SettingsRoute : NavigableRoute()

    @Serializable
    internal object TimerRoute : NavigableRoute()

    @Serializable
    internal object HelpAndAboutRoute : NavigableRoute()

    @Serializable
    internal object WorkshopGeneratorRoute : NavigableRoute()

    @Serializable
    internal object TipsAndAdviceRoute : NavigableRoute()

    @Serializable
    internal object GamesPageRoute : NavigableRoute()

    @Serializable
    internal object PeoplePageRoute : NavigableRoute()

    @Serializable
    internal object EmotionPageRoute : NavigableRoute()

    @Serializable
    internal object ThesaurusPageRoute : NavigableRoute()

    @Serializable
    internal object PrivacyRoute : NavigableRoute()

}

internal sealed class NavigableScreens(
    @param:StringRes @field:StringRes internal val titleResource: Int,
    @param:StringRes @field:StringRes internal val contentDescription: Int,
    internal val icon: ImageVector,
    internal val route: NavigableRoute,
    internal val shouldShowExtraMenu: Boolean,
) {
    @Immutable
    internal object SuggestionGeneratorScreen : NavigableScreens(
        titleResource = R.string.suggestions_activity_title,
        contentDescription = R.string.go_to_suggestion_generator,
        icon = Icons.Outlined.Lightbulb,
        route = SuggestionGeneratorRoute,
        shouldShowExtraMenu = true,
    )

    @Immutable
    internal object SettingsScreen : NavigableScreens(
        titleResource = R.string.settings_activity_title,
        contentDescription = R.string.go_to_settings_screen,
        icon = Icons.Outlined.Settings,
        route = SettingsRoute,
        shouldShowExtraMenu = false,
    )

    @Immutable
    internal object TimerScreen : NavigableScreens(
        titleResource = R.string.timer_activity_title,
        contentDescription = R.string.go_to_timer_screen,
        icon = Icons.Outlined.Timer,
        route = TimerRoute,
        shouldShowExtraMenu = false,
    )

    @Immutable
    internal object HelpAndAboutScreen : NavigableScreens(
        titleResource = R.string.navigation_help_and_feedback,
        contentDescription = R.string.go_to_help_and_feedback_screen,
        icon = Icons.Outlined.Info,
        route = HelpAndAboutRoute,
        shouldShowExtraMenu = false,
    )

    @Immutable
    internal object WorkshopGeneratorScreen : NavigableScreens(
        titleResource = R.string.navigation_workshop_generator,
        contentDescription = R.string.go_to_workshop_generator_screen,
        icon = Icons.Outlined.Games,
        route = WorkshopGeneratorRoute,
        shouldShowExtraMenu = false,
    )

    @Immutable
    internal object TipsAndAdviceScreen : NavigableScreens(
        titleResource = R.string.navigation_tips_and_advice,
        contentDescription = R.string.go_to_tips_and_advice_screen,
        icon = Icons.Outlined.TipsAndUpdates,
        route = TipsAndAdviceRoute,
        shouldShowExtraMenu = true,
    )


    @Immutable
    internal object GamesPageScreen : NavigableScreens(
        titleResource = R.string.navigation_games,
        contentDescription = R.string.go_to_games_screen,
        icon = Icons.Outlined.Games,
        route = GamesPageRoute,
        shouldShowExtraMenu = false,
    )

    @Immutable
    internal object PeoplePageScreen : NavigableScreens(
        titleResource = R.string.navigation_people,
        contentDescription = R.string.go_to_navigation_people_screen,
        icon = Icons.Outlined.People,
        route = PeoplePageRoute,
        shouldShowExtraMenu = false,
    )

    @Immutable
    internal object EmotionsPageScreen : NavigableScreens(
        titleResource = R.string.navigation_emotions_reference,
        contentDescription = R.string.go_to_emotions_reference_screen,
        icon = Icons.Outlined.EmojiEmotions,
        route = EmotionPageRoute,
        shouldShowExtraMenu = false,
    )

    @Immutable
    internal object ThesaurusPageScreen : NavigableScreens(
        titleResource = R.string.navigation_thesaurus,
        contentDescription = R.string.go_to_thesaurus_screen,
        icon = Icons.Filled.Book,
        route = ThesaurusPageRoute,
        shouldShowExtraMenu = false,
    )


    @Immutable
    internal object PrivacyScreen : NavigableScreens(
        titleResource = R.string.navigation_privacy_information,
        contentDescription = R.string.go_to_privacy_information,
        icon = Icons.Filled.PrivacyTip,
        route = NavigableRoute.PrivacyRoute,
        shouldShowExtraMenu = false,
    )
}

internal fun routeToScreen(route: NavigableRoute): NavigableScreens {
    return when (route) {
        EmotionPageRoute -> NavigableScreens.EmotionsPageScreen
        GamesPageRoute -> NavigableScreens.GamesPageScreen
        HelpAndAboutRoute -> NavigableScreens.HelpAndAboutScreen
        PeoplePageRoute -> NavigableScreens.PeoplePageScreen
        SettingsRoute -> NavigableScreens.SettingsScreen
        SuggestionGeneratorRoute -> NavigableScreens.SuggestionGeneratorScreen
        ThesaurusPageRoute -> NavigableScreens.ThesaurusPageScreen
        TimerRoute -> NavigableScreens.TimerScreen
        TipsAndAdviceRoute -> NavigableScreens.TipsAndAdviceScreen
        WorkshopGeneratorRoute -> NavigableScreens.WorkshopGeneratorScreen
        NavigableRoute.PrivacyRoute -> NavigableScreens.PrivacyScreen
    }
}

// TODO: consider making this CompositionLocal
// TODO:  consider passing callbacks instead of direct refs to routes - but that's another story
//internal class ImprovToolsNavigator(
//    val doNavigateToNavigableScreen: (NavigableScreens) -> Unit,
//    val doNavigateToNavigableRoute: (NavigableRoute) -> Unit,
//)
