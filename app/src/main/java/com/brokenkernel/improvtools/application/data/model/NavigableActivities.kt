package com.brokenkernel.improvtools.application.data.model

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.brokenkernel.improvtools.R


internal sealed class ActiveScreen(
    val icon: ImageVector,
    val contentDescription: String,
    val route: String,
) {
    object SuggestionGenerator: ActiveScreen(
        icon = Icons.Default.Home, // TODO - placeholder
        contentDescription = "Go To Suggestion Generator",
        route = "suggestion_generator_screen",
    )

    object About: ActiveScreen(
        icon = Icons.Outlined.Info, // TODO - placeholder
        contentDescription = "Go to about screen",
        route = "about_screen"
    )

    object Settings: ActiveScreen(
        icon = Icons.Outlined.Settings,
        contentDescription = "Go to settings screen",
        route = "settings_screen"
    )
}


internal enum class NavigableActivities(
    @StringRes internal val titleResource: Int,
    internal val activeScreen: ActiveScreen) {
    SuggestionGenerator(
        titleResource = R.string.suggestions_activity_title,
        activeScreen = ActiveScreen.SuggestionGenerator,
    ),
    Settings(
        titleResource = R.string.settings_activity_title,
        activeScreen = ActiveScreen.Settings,
    ),
    About(
        titleResource = R.string.about_activity_title,
        activeScreen = ActiveScreen.About,
    ),
}