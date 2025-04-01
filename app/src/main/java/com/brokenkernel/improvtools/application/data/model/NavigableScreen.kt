package com.brokenkernel.improvtools.application.data.model

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector
import com.brokenkernel.improvtools.R

// import kotlinx.serialization.Seralizable

//@Serializable
internal sealed class NavigableScreens(
    @StringRes internal val titleResource: Int,
    @StringRes internal val contentDescription: Int,
    internal val icon: ImageVector,
    internal val route: String,
    internal val shouldShowExtraMenu: Boolean,
) {
    @Immutable
    internal object SuggestionGenerator : NavigableScreens(
        titleResource = R.string.suggestions_activity_title,
        contentDescription = R.string.go_to_suggestion_generator,
        icon = Icons.Outlined.Lightbulb,
        route = "suggestion_generator_screen",
        shouldShowExtraMenu = true,
    )

    @Immutable
    internal object Settings : NavigableScreens(
        titleResource = R.string.settings_activity_title,
        contentDescription = R.string.go_to_settings_screen,
        icon = Icons.Outlined.Settings,
        route = "settings_screen",
        shouldShowExtraMenu = false,
    )

    @Immutable
    internal object Timer : NavigableScreens(
        titleResource = R.string.timer_activity_title,
        contentDescription = R.string.go_to_timer_screen,
        icon = Icons.Outlined.Timer,
        route = "timer_screen",
        shouldShowExtraMenu = false,
    )

    @Immutable
    internal object HelpAndAbout : NavigableScreens(
        titleResource = R.string.navigation_help_and_feedback,
        contentDescription = R.string.go_to_help_and_feedback_screen,
        icon = Icons.Outlined.Info,
        route = "about_screen",
        shouldShowExtraMenu = false,
    )

    companion object {
        fun byRoute(route: String?): NavigableScreens? {
            return NavigableScreens::class.sealedSubclasses.map { it ->
                it.objectInstance as NavigableScreens
            }
                .find { ns ->
                    ns.route == route
                }
        }

    }
}
