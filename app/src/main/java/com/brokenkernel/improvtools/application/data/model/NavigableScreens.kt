package com.brokenkernel.improvtools.application.data.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Copyright
import androidx.compose.material.icons.filled.DeviceUnknown
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.encyclopaedia.presentation.view.TipsAndAdviceTabMenu
import com.brokenkernel.improvtools.settings.presentation.view.SuggestionsTabMenu
import com.ramcosta.composedestinations.generated.destinations.AboutTabDestination
import com.ramcosta.composedestinations.generated.destinations.EmotionTabDestination
import com.ramcosta.composedestinations.generated.destinations.GamesTabDestination
import com.ramcosta.composedestinations.generated.destinations.GlossaryTabDestination
import com.ramcosta.composedestinations.generated.destinations.LibrariesTabDestination
import com.ramcosta.composedestinations.generated.destinations.PeopleTabDestination
import com.ramcosta.composedestinations.generated.destinations.PrivacyTabDestination
import com.ramcosta.composedestinations.generated.destinations.SettingsTabDestination
import com.ramcosta.composedestinations.generated.destinations.SuggestionsTabDestination
import com.ramcosta.composedestinations.generated.destinations.ThesaurusTabAllItemsDestination
import com.ramcosta.composedestinations.generated.destinations.ThesaurusTabSingleWordDestination
import com.ramcosta.composedestinations.generated.destinations.TimerTabDestination
import com.ramcosta.composedestinations.generated.destinations.TipsAndAdviceTabDestination
import com.ramcosta.composedestinations.generated.destinations.TongueTwisterTabDestination
import com.ramcosta.composedestinations.spec.Direction
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import com.ramcosta.composedestinations.spec.TypedDestinationSpec

/**
 * A [[DirectionDestinationSpec]] is a destination that can be navigated to. It should display anique inner content.
 * If somewhere else can display a button to click to get there, it needs a route.
 */
internal enum class NavigableScreens(
    @param:StringRes @field:StringRes internal val titleResource: Int,
    @param:StringRes @field:StringRes internal val contentDescription: Int,
    // there must be a better way to handle what is effectively a union type ...
    private val iconVector: ImageVector? = null,
    @param:DrawableRes @field:DrawableRes private val iconDrawable: Int? = null,
    internal val matchingRoute: Direction,
    val extraMenu: @Composable ((ImprovToolsAppState) -> Unit)? = null,
) {
    SuggestionGeneratorScreen(
        titleResource = R.string.suggestions_activity_title,
        contentDescription = R.string.go_to_suggestion_generator,
        iconVector = Icons.Outlined.Lightbulb,
        matchingRoute = SuggestionsTabDestination,
        extraMenu = { improvToolsAppState ->
            SuggestionsTabMenu(
                expanded = improvToolsAppState.extraMenuExpandedState,
                onDismiss = {
                    improvToolsAppState.extraMenuExpandedState =
                        !improvToolsAppState.extraMenuExpandedState
                },
            )
        },
    ),

    SettingsScreen(
        titleResource = R.string.settings_activity_title,
        contentDescription = R.string.go_to_settings_screen,
        iconVector = Icons.Outlined.Settings,
        matchingRoute = SettingsTabDestination,

    ),

    TimerScreen(
        titleResource = R.string.timer_activity_title,
        contentDescription = R.string.go_to_timer_screen,
        iconVector = Icons.Outlined.Timer,
        matchingRoute = TimerTabDestination,

    ),

    AboutScreen(
        titleResource = R.string.navigation_help_and_feedback,
        contentDescription = R.string.go_to_help_and_feedback_screen,
        iconVector = Icons.Outlined.Info,
        matchingRoute = AboutTabDestination,

    ),

    TipsAndAdviceScreen(
        titleResource = R.string.navigation_tips_and_advice,
        contentDescription = R.string.go_to_tips_and_advice_screen,
        iconVector = Icons.Outlined.TipsAndUpdates,
        matchingRoute = TipsAndAdviceTabDestination,
        extraMenu = { improvToolsAppState ->
            TipsAndAdviceTabMenu(
                expanded = improvToolsAppState.extraMenuExpandedState,
                onDismiss = {
                    improvToolsAppState.extraMenuExpandedState = !improvToolsAppState.extraMenuExpandedState
                },
            )
        },
    ),

    GamesPageScreen(
        titleResource = R.string.navigation_games,
        contentDescription = R.string.go_to_games_screen,
        iconVector = Icons.Outlined.Games,
        matchingRoute = GamesTabDestination,

    ),

    PeoplePageScreen(
        titleResource = R.string.navigation_people,
        contentDescription = R.string.go_to_navigation_people_screen,
        iconVector = Icons.Outlined.People,
        matchingRoute = PeopleTabDestination,

    ),

    GlossaryPageScreen(
        titleResource = R.string.navigation_glossary,
        contentDescription = R.string.go_to_glossary,
        // TODO: figure out better icon (esp since both Glossary and Thesaurus. Consider supporting Drawable)
        iconVector = Icons.Filled.Book,
        matchingRoute = GlossaryTabDestination,

    ),

    EmotionsPageScreen(
        titleResource = R.string.navigation_emotions_reference,
        contentDescription = R.string.go_to_emotions_reference_screen,
        iconVector = Icons.Outlined.EmojiEmotions,
        matchingRoute = EmotionTabDestination,

    ),

    ThesaurusPageScreen(
        titleResource = R.string.navigation_thesaurus,
        contentDescription = R.string.go_to_thesaurus_screen,
        iconVector = Icons.Outlined.Book,
        matchingRoute = ThesaurusTabAllItemsDestination,
    ),

    PrivacyScreen(
        titleResource = R.string.navigation_privacy_information,
        contentDescription = R.string.go_to_privacy_information,
        iconVector = Icons.Filled.PrivacyTip,
        matchingRoute = PrivacyTabDestination,
    ),

    LibrariesScreen(
        titleResource = R.string.navigation_libraries_information,
        contentDescription = R.string.go_to_libraries_information,
        iconVector = Icons.Default.Copyright,
        matchingRoute = LibrariesTabDestination,
    ),

    TongueTwisterScreen(
        titleResource = R.string.tongue_twisters,
        contentDescription = R.string.go_to_tongue_twisters,
        iconDrawable = R.drawable.ent_24px,
        matchingRoute = TongueTwisterTabDestination,
    ),
    ;

    @Composable
    internal fun icon(): ImageVector {
        // there must be a better way to handle what is effectively a union type ...
        if (iconVector != null) {
            return iconVector
        } else if (iconDrawable != null) {
            return ImageVector.vectorResource(iconDrawable)
        } else {
            // meh, likely a better way to handle this
            return Icons.Default.DeviceUnknown
        }
    }

    companion object {
        fun byRoute(route: TypedDestinationSpec<*>): NavigableScreens {
            // todo: deal with invalid route, for now force non-null for $reasons
            // todo: deal with finding right screen. This is a regression against standard compose
            return when (route) {
                ThesaurusTabSingleWordDestination -> ThesaurusPageScreen
                else -> NavigableScreens.entries.find { it.matchingRoute == route } ?: SuggestionGeneratorScreen
            }
        }
    }
}
