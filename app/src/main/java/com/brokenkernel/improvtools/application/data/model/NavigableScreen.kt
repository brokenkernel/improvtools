package com.brokenkernel.improvtools.application.data.model

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Copyright
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.outlined.Book
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
import com.brokenkernel.improvtools.application.data.model.NavigableRoute.GlossaryRoute
import com.brokenkernel.improvtools.application.data.model.NavigableRoute.HelpAndAboutRoute
import com.brokenkernel.improvtools.application.data.model.NavigableRoute.LibrariesRoute
import com.brokenkernel.improvtools.application.data.model.NavigableRoute.PeoplePageRoute
import com.brokenkernel.improvtools.application.data.model.NavigableRoute.PrivacyRoute
import com.brokenkernel.improvtools.application.data.model.NavigableRoute.SettingsRoute
import com.brokenkernel.improvtools.application.data.model.NavigableRoute.SuggestionGeneratorRoute
import com.brokenkernel.improvtools.application.data.model.NavigableRoute.ThesaurusPageRoute
import com.brokenkernel.improvtools.application.data.model.NavigableRoute.TimerRoute
import com.brokenkernel.improvtools.application.data.model.NavigableRoute.TipsAndAdviceRoute
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
    internal object TipsAndAdviceRoute : NavigableRoute()

    @Serializable
    internal object GamesPageRoute : NavigableRoute()

    @Serializable
    internal object PeoplePageRoute : NavigableRoute()

    @Serializable
    internal object GlossaryRoute : NavigableRoute()

    @Serializable
    internal object EmotionPageRoute : NavigableRoute()

    @Serializable
    internal object ThesaurusPageRoute : NavigableRoute()

    @Serializable
    internal class ThesaurusWordRoute(val word: String) : NavigableRoute()

    @Serializable
    internal object PrivacyRoute : NavigableRoute()

    @Serializable
    internal object LibrariesRoute : NavigableRoute()
}

internal sealed class NavigableScreens(
    @param:StringRes @field:StringRes internal val titleResource: Int,
    @param:StringRes @field:StringRes internal val contentDescription: Int,
    internal val icon: ImageVector,
    internal val matchingRoutes: Set<NavigableRoute>,
    internal val navigationCallback: (itas: ImprovToolsAppState) -> Unit = { itas ->
        // TODO: FIXME
        itas.navigateTo(matchingRoutes.first())
    }, // TODO: remove default
) {
    @Immutable
    internal object SuggestionGeneratorScreen : NavigableScreens(
        titleResource = R.string.suggestions_activity_title,
        contentDescription = R.string.go_to_suggestion_generator,
        icon = Icons.Outlined.Lightbulb,
        matchingRoutes = setOf(SuggestionGeneratorRoute),
    )

    @Immutable
    internal object SettingsScreen : NavigableScreens(
        titleResource = R.string.settings_activity_title,
        contentDescription = R.string.go_to_settings_screen,
        icon = Icons.Outlined.Settings,
        matchingRoutes = setOf(SettingsRoute),
    )

    @Immutable
    internal object TimerScreen : NavigableScreens(
        titleResource = R.string.timer_activity_title,
        contentDescription = R.string.go_to_timer_screen,
        icon = Icons.Outlined.Timer,
        matchingRoutes = setOf(TimerRoute),
    )

    @Immutable
    internal object HelpAndAboutScreen : NavigableScreens(
        titleResource = R.string.navigation_help_and_feedback,
        contentDescription = R.string.go_to_help_and_feedback_screen,
        icon = Icons.Outlined.Info,
        matchingRoutes = setOf(HelpAndAboutRoute),
    )

    @Immutable
    internal object TipsAndAdviceScreen : NavigableScreens(
        titleResource = R.string.navigation_tips_and_advice,
        contentDescription = R.string.go_to_tips_and_advice_screen,
        icon = Icons.Outlined.TipsAndUpdates,
        matchingRoutes = setOf(TipsAndAdviceRoute),
    )

    @Immutable
    internal object GamesPageScreen : NavigableScreens(
        titleResource = R.string.navigation_games,
        contentDescription = R.string.go_to_games_screen,
        icon = Icons.Outlined.Games,
        matchingRoutes = setOf(GamesPageRoute),
    )

    @Immutable
    internal object PeoplePageScreen : NavigableScreens(
        titleResource = R.string.navigation_people,
        contentDescription = R.string.go_to_navigation_people_screen,
        icon = Icons.Outlined.People,
        matchingRoutes = setOf(PeoplePageRoute),
    )

    @Immutable
    internal object GlossaryPageScreen : NavigableScreens(
        titleResource = R.string.navigation_glossary,
        contentDescription = R.string.go_to_glossary,
        // TODO: figure out better icon (esp since both Glossary and Thesaurus. Consider supporting Drawable)
        icon = Icons.Filled.Book,
        matchingRoutes = setOf(GlossaryRoute),
    )

    @Immutable
    internal object EmotionsPageScreen : NavigableScreens(
        titleResource = R.string.navigation_emotions_reference,
        contentDescription = R.string.go_to_emotions_reference_screen,
        icon = Icons.Outlined.EmojiEmotions,
        matchingRoutes = setOf(EmotionPageRoute),
    )

    @Immutable
    internal object ThesaurusPageScreen : NavigableScreens(
        titleResource = R.string.navigation_thesaurus,
        contentDescription = R.string.go_to_thesaurus_screen,
        icon = Icons.Outlined.Book,
        matchingRoutes = setOf(ThesaurusPageRoute),
    )

    @Immutable
    internal object PrivacyScreen : NavigableScreens(
        titleResource = R.string.navigation_privacy_information,
        contentDescription = R.string.go_to_privacy_information,
        icon = Icons.Filled.PrivacyTip,
        matchingRoutes = setOf(PrivacyRoute),
    )

    @Immutable
    internal object LibrariesScreen : NavigableScreens(
        titleResource = R.string.navigation_libraries_information,
        contentDescription = R.string.go_to_libraries_information,
        icon = Icons.Default.Copyright,
        matchingRoutes = setOf(LibrariesRoute),
    )
}
