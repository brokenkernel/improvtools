package com.brokenkernel.improvtools.application.data.model

import android.app.Activity
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Info
import androidx.compose.ui.graphics.vector.ImageVector
import com.brokenkernel.improvtools.R

//typealias ResourceId = Int // TODO consider inline value class instead

internal sealed class ActiveScreen(
    val icon: ImageVector,
    val contentDescription: String,
    val title: String,
    val route: String,
) {
    object SuggestionGenerator: ActiveScreen(
        icon = Icons.Default.Home, // TODO - placeholder
        contentDescription = "Go To Suggestion Generator=",
        title = "Suggestion Generator",
        route = "suggestion_generator_screen",
    )

    object About: ActiveScreen(
        icon = Icons.Outlined.Info, // TODO - placeholder
        contentDescription = "Go to about screen",
        title = "About",
        route = "about_screen"
    )
}


internal enum class NavigableActivities(internal val titleResource: Int, internal val activeScreen: ActiveScreen) {
    SuggestionGenerator(
        titleResource = R.string.suggestions_activity_title,
        activeScreen = ActiveScreen.SuggestionGenerator,
    ),
    Settings(
        titleResource = R.string.settings_activity_title,
        activeScreen = ActiveScreen.About, // TODO - placeholder
    ),
    About(
        titleResource = R.string.about_activity_title,
        activeScreen = ActiveScreen.About,
    ),
}