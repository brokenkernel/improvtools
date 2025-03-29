package com.brokenkernel.improvtools.application.data.model

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.brokenkernel.improvtools.R


internal enum class NavigableScreens(
    @StringRes internal val titleResource: Int,
    internal val contentDescription: String,
    internal val icon: ImageVector,
    internal val route: String,

    ) {

    SuggestionGenerator(
        titleResource = R.string.suggestions_activity_title,
        contentDescription = "Go To Suggestion Generator",
        icon = Icons.Outlined.Lightbulb,
        route = "suggestion_generator_screen",
    ),
    Settings(
        titleResource = R.string.settings_activity_title,
        contentDescription = "Go to about screen",
        icon = Icons.Outlined.Settings,
        route = "settings_screen",
    ),
    About(
        titleResource = R.string.about_activity_title,
        contentDescription = "Go to about screen",
        icon = Icons.Outlined.Info, // TODO - placeholder
        route = "about_screen",
    ),
}