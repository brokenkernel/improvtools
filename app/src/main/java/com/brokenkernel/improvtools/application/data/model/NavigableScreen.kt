package com.brokenkernel.improvtools.application.data.model

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BookOnline
import androidx.compose.material.icons.outlined.Games
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material.icons.outlined.TipsAndUpdates
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.application.data.model.NavigableRoute.EncyclopaediaRoute
import com.brokenkernel.improvtools.application.data.model.NavigableRoute.HelpAndAboutRoute
import com.brokenkernel.improvtools.application.data.model.NavigableRoute.SettingsRoute
import com.brokenkernel.improvtools.application.data.model.NavigableRoute.SuggestionGeneratorRoute
import com.brokenkernel.improvtools.application.data.model.NavigableRoute.TimerRoute
import com.brokenkernel.improvtools.application.data.model.NavigableRoute.TipsAndAdviceRoute
import com.brokenkernel.improvtools.application.data.model.NavigableRoute.WorkshopGeneratorRoute
import kotlinx.serialization.Serializable

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
    internal object EncyclopaediaRoute : NavigableRoute()

}


internal sealed class NavigableScreens(
    @StringRes internal val titleResource: Int,
    @StringRes internal val contentDescription: Int,
    internal val icon: ImageVector,
    internal val route: NavigableRoute,
    internal val shouldShowExtraMenu: Boolean,
) {
    @Immutable
    internal object SuggestionGenerator : NavigableScreens(
        titleResource = R.string.suggestions_activity_title,
        contentDescription = R.string.go_to_suggestion_generator,
        icon = Icons.Outlined.Lightbulb,
        route = SuggestionGeneratorRoute,
        shouldShowExtraMenu = true,
    )

    @Immutable
    internal object Settings : NavigableScreens(
        titleResource = R.string.settings_activity_title,
        contentDescription = R.string.go_to_settings_screen,
        icon = Icons.Outlined.Settings,
        route = SettingsRoute,
        shouldShowExtraMenu = false,
    )

    @Immutable
    internal object Timer : NavigableScreens(
        titleResource = R.string.timer_activity_title,
        contentDescription = R.string.go_to_timer_screen,
        icon = Icons.Outlined.Timer,
        route = TimerRoute,
        shouldShowExtraMenu = false,
    )

    @Immutable
    internal object HelpAndAbout : NavigableScreens(
        titleResource = R.string.navigation_help_and_feedback,
        contentDescription = R.string.go_to_help_and_feedback_screen,
        icon = Icons.Outlined.Info,
        route = HelpAndAboutRoute,
        shouldShowExtraMenu = false,
    )

    @Immutable
    internal object WorkshopGenerator : NavigableScreens(
        titleResource = R.string.navigation_workshop_generator,
        contentDescription = R.string.go_to_workshop_generator_screen,
        icon = Icons.Outlined.Games,
        route = WorkshopGeneratorRoute,
        shouldShowExtraMenu = false,
    )

    @Immutable
    internal object TipsAndAdvice : NavigableScreens(
        titleResource = R.string.navigation_tips_and_advice,
        contentDescription = R.string.go_to_tips_and_advice_screen,
        icon = Icons.Outlined.TipsAndUpdates,
        route = TipsAndAdviceRoute,
        shouldShowExtraMenu = false,
    )


    @Immutable
    internal object Encyclopaedia : NavigableScreens(
        titleResource = R.string.navigation_encyclopaedia,
        contentDescription = R.string.go_to_encyclopaedia_screen,
        icon = Icons.Outlined.BookOnline,
        route = EncyclopaediaRoute,
        shouldShowExtraMenu = false,
    )
}
