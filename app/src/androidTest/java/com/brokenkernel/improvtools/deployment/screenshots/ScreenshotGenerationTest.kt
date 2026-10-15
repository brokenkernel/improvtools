package com.brokenkernel.improvtools.deployment.screenshots

import androidx.annotation.StringRes
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.brokenkernel.improvtools.application.data.model.NavigableScreens
import com.brokenkernel.improvtools.application.presentation.view.OuterContentForMasterScreen
import com.brokenkernel.improvtools.infrastructure.HiltComponentActitivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import tools.fastlane.screengrab.Screengrab
import tools.fastlane.screengrab.UiAutomatorScreenshotStrategy
import tools.fastlane.screengrab.cleanstatusbar.CleanStatusBar
import tools.fastlane.screengrab.locale.LocaleTestRule

sealed class BaseScreenshotGenerationTest {
    @get:Rule(order = 0)
    var hiltRule: HiltAndroidRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    val composeTestRule: ComposeContentTestRule =
        createAndroidComposeRule<HiltComponentActitivity>()

    @Rule(order = 1)
    @JvmField
    val localeTestRule: LocaleTestRule = LocaleTestRule()

    @Before
    fun setupAppNavHost() {
        hiltRule.inject()
        Screengrab.setDefaultScreenshotStrategy(UiAutomatorScreenshotStrategy())

        composeTestRule.setContent {
            OuterContentForMasterScreen(NavigableScreens.SuggestionGeneratorScreen)
        }
        composeTestRule.waitForIdle()
    }

    companion object {
        @BeforeClass
        @JvmStatic
        fun beforeAll() {
            CleanStatusBar.enableWithDefaults()
        }

        @AfterClass
        @JvmStatic
        fun afterAll() {
            CleanStatusBar.disable()
        }
    }

    fun getString(@StringRes id: Int): String {
        return getInstrumentation().targetContext.getString(id)
    }
}

@HiltAndroidTest
@RunWith(Parameterized::class)
internal class ScreenshotGeneralPerNavigableScreenTest(
    private val navigableScreen: NavigableScreens,
) : BaseScreenshotGenerationTest() {

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data(): Iterable<Array<Any>> {
            return arrayListOf(
                arrayOf(NavigableScreens.SuggestionGeneratorScreen),
                arrayOf(NavigableScreens.TimerScreen),
                arrayOf(NavigableScreens.TipsAndAdviceScreen),
                arrayOf(NavigableScreens.PeoplePageScreen),
                arrayOf(NavigableScreens.GamesPageScreen),
//                arrayOf(NavigableScreens.EmotionsPageScreen), // TODO: there are now two copies of this button. Maybe add TestTag or more specifically use menu instead of screens? Unsure what to do .
            )
        }
    }

    @Test
    fun takeScreenshotOfNavigableScreen() {
        composeTestRule.onNodeWithContentDescription(
            getString(navigableScreen.contentDescription),
            useUnmergedTree = true,
        )
            .performClick()
        composeTestRule.waitForIdle()

        Screengrab.screenshot(navigableScreen.matchingRoute::class.simpleName + "_screen_baseline")
    }
}
