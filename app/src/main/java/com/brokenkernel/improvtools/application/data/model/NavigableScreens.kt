package com.brokenkernel.improvtools.application.data.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.application.api.AboutNavigationKey
import com.brokenkernel.improvtools.application.api.LibrariesNavigationKey
import com.brokenkernel.improvtools.application.api.PrivacyNavigationKey
import com.brokenkernel.improvtools.application.api.SettingsNavigationKey
import com.brokenkernel.improvtools.buzzer.api.BuzzerNavigationKey
import com.brokenkernel.improvtools.coreinfra.ImprovToolsNavigationKey
import com.brokenkernel.improvtools.encyclopaedia.android.api.EmotionsPageNavigationKey
import com.brokenkernel.improvtools.encyclopaedia.android.api.GamesPageNavigationKey
import com.brokenkernel.improvtools.encyclopaedia.android.api.GlossaryPageNavigationKey
import com.brokenkernel.improvtools.encyclopaedia.android.api.PeoplePageNavigationKey
import com.brokenkernel.improvtools.encyclopaedia.android.api.ThesaurusAllItemsPageNavigationKey
import com.brokenkernel.improvtools.encyclopaedia.android.api.ThesaurusSingleWordPageNavigationKey
import com.brokenkernel.improvtools.encyclopaedia.android.api.TipsAndAdviceNavigationKey
import com.brokenkernel.improvtools.suggestions.api.SuggestionsScreenNavigationKey
import com.brokenkernel.improvtools.timer.api.TimerNavigationKey
import com.brokenkernel.improvtools.tonguetwister.api.TongueTwisterNavigationKey

/**
 * A [[NavigableScreens]] is a destination that can be navigated to. It should display unique inner content.
 * If somewhere else can display a button to click to get there, it needs a route.
 */
internal enum class NavigableScreens(
    @param:StringRes @field:StringRes internal val titleResource: Int,
    @param:StringRes @field:StringRes internal val contentDescription: Int,
    @param:DrawableRes @field:DrawableRes private val iconDrawable: Int,
    internal val matchingRoute: ImprovToolsNavigationKey,
    val extraMenu: @Composable ((ImprovToolsAppState) -> Unit)? = null,
) {
    SuggestionGeneratorScreen(
        titleResource = R.string.suggestions_activity_title,
        contentDescription = R.string.go_to_suggestion_generator,
        iconDrawable = R.drawable.lightbulb_24dp_1f1f1f_fill0_wght400_grad0_opsz24,
        matchingRoute = SuggestionsScreenNavigationKey,
    ),

    SettingsScreen(
        titleResource = R.string.settings_activity_title,
        contentDescription = R.string.go_to_settings_screen,
        iconDrawable = R.drawable.settings_24dp_1f1f1f_fill0_wght400_grad0_opsz24,
        matchingRoute = SettingsNavigationKey,

    ),

    TimerScreen(
        titleResource = R.string.timer_activity_title,
        contentDescription = R.string.go_to_timer_screen,
        iconDrawable = R.drawable.timer_24dp_1f1f1f_fill0_wght400_grad0_opsz24,
        matchingRoute = TimerNavigationKey,

    ),

    AboutScreen(
        titleResource = R.string.navigation_help_and_feedback,
        contentDescription = R.string.go_to_help_and_feedback_screen,
        iconDrawable = R.drawable.info_24dp_1f1f1f_fill0_wght400_grad0_opsz24,
        matchingRoute = AboutNavigationKey,
    ),

    TipsAndAdviceScreen(
        titleResource = R.string.navigation_tips_and_advice,
        contentDescription = R.string.go_to_tips_and_advice_screen,
        iconDrawable = R.drawable.cognition_24dp_1f1f1f_fill0_wght400_grad0_opsz24,
        matchingRoute = TipsAndAdviceNavigationKey,
    ),

    GamesPageScreen(
        titleResource = R.string.navigation_games,
        contentDescription = R.string.go_to_games_screen,
        iconDrawable = R.drawable.toys_and_games_24dp_1f1f1f_fill0_wght400_grad0_opsz24,
        matchingRoute = GamesPageNavigationKey,

    ),

    PeoplePageScreen(
        titleResource = R.string.navigation_people,
        contentDescription = R.string.go_to_navigation_people_screen,
        iconDrawable = R.drawable.emoji_people_24dp_1f1f1f_fill0_wght400_grad0_opsz24,
        matchingRoute = PeoplePageNavigationKey,

    ),

    GlossaryPageScreen(
        titleResource = R.string.navigation_glossary,
        contentDescription = R.string.go_to_glossary,
        // TODO: figure out better icon (esp since both Glossary and Thesaurus. Consider supporting Drawable)
        iconDrawable = R.drawable.book_2_24dp_1f1f1f_fill0_wght400_grad0_opsz24,
        matchingRoute = GlossaryPageNavigationKey,

    ),

    EmotionsPageScreen(
        titleResource = R.string.navigation_emotions_reference,
        contentDescription = R.string.go_to_emotions_reference_screen,
        iconDrawable = R.drawable.face_2_24dp_1f1f1f_fill0_wght400_grad0_opsz24,
        matchingRoute = EmotionsPageNavigationKey,

    ),

    ThesaurusPageScreen(
        titleResource = R.string.navigation_thesaurus,
        contentDescription = R.string.go_to_thesaurus_screen,
        iconDrawable = R.drawable.dictionary_24dp_1f1f1f_fill0_wght400_grad0_opsz24,
        matchingRoute = ThesaurusAllItemsPageNavigationKey,
    ),

    PrivacyScreen(
        titleResource = R.string.navigation_privacy_information,
        contentDescription = R.string.go_to_privacy_information,
        iconDrawable = R.drawable.privacy_tip_24dp_1f1f1f_fill0_wght400_grad0_opsz24,
        matchingRoute = PrivacyNavigationKey,
    ),

    LibrariesScreen(
        titleResource = R.string.navigation_libraries_information,
        contentDescription = R.string.go_to_libraries_information,
        iconDrawable = R.drawable.copyright_24dp_1f1f1f_fill0_wght400_grad0_opsz24,
        matchingRoute = LibrariesNavigationKey,
    ),

    TongueTwisterScreen(
        titleResource = R.string.tongue_twisters,
        contentDescription = R.string.go_to_tongue_twisters,
        iconDrawable = R.drawable.ent_24px,
        matchingRoute = TongueTwisterNavigationKey,
    ),

    BuzzerScreen(
        titleResource = R.string.navigation_buzzer,
        contentDescription = R.string.go_to_buzzer,
        iconDrawable = R.drawable.surround_sound_24dp_1f1f1f_fill0_wght400_grad0_opsz24,
        matchingRoute = BuzzerNavigationKey,
    ),
    ;

    @Composable
    internal fun icon(): ImageVector {
        return ImageVector.vectorResource(iconDrawable)
    }

    companion object {
        fun byRoute(route: ImprovToolsNavigationKey): NavigableScreens {
            // todo: deal with invalid route, for now force non-null for $reasons
            // todo: deal with finding right screen. This is a regression against standard compose
            return when (route) {
                is ThesaurusSingleWordPageNavigationKey -> ThesaurusPageScreen
                else -> NavigableScreens.entries.find { it.matchingRoute == route } ?: SuggestionGeneratorScreen
            }
        }
    }
}
