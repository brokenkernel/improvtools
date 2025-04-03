package com.brokenkernel.improvtools.deployment.screenshots

import androidx.annotation.StringRes
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.brokenkernel.improvtools.HiltComponentActitivity
import com.brokenkernel.improvtools.OuterContentForMasterScreen
import com.brokenkernel.improvtools.R
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import tools.fastlane.screengrab.Screengrab
import tools.fastlane.screengrab.UiAutomatorScreenshotStrategy

@HiltAndroidTest
class ScreenshotGenerationTest {

    @get:Rule(order = 0)
    var hiltRule: HiltAndroidRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule: ComposeContentTestRule = createAndroidComposeRule<HiltComponentActitivity>()

//    lateinit var navController: TestNavHostController
//
//    @get:Rule
//    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setupAppNavHost() {
        hiltRule.inject()
        Screengrab.setDefaultScreenshotStrategy(UiAutomatorScreenshotStrategy())

        composeTestRule.setContent {
//            navController = TestNavHostController(LocalContext.current)
//            navController.navigatorProvider.addNavigator(ComposeNavigator())
//            DrawerNavGraph(drawerNavController = navController)
            OuterContentForMasterScreen()
        }
    }


    @Test
    fun takeSettingsPageScreenshot() {
        composeTestRule.onNode(
            hasContentDescription(getString(R.string.navigation_app_menu))
        )
            .performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNode(
            hasContentDescription(getString(R.string.go_to_settings_screen))
        )
            .performClick()
        composeTestRule.waitForIdle()



        Screengrab.screenshot("settings_screen_baseline")

    }


    @Test
    fun takeSuggestionsPageScreenshot() {
        composeTestRule.onNode(
            hasContentDescription(getString(R.string.navigation_app_menu))
        )
            .performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNode(
            hasContentDescription(getString(R.string.go_to_suggestion_generator))
        )
            .performClick()
        composeTestRule.waitForIdle()



        Screengrab.screenshot("suggestions_screen_baseline")

    }

    @Test
    fun takeTimerPageScreenshot() {
        composeTestRule.onNode(
            hasContentDescription(getString(R.string.navigation_app_menu))
        )
            .performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNode(
            hasContentDescription(getString(R.string.go_to_timer_screen))
        )
            .performClick()
        composeTestRule.waitForIdle()


        Screengrab.screenshot("timer_screen_baseline")
    }


    @Test
    fun takeAboutPageScreenshot() {
        composeTestRule.onNode(
            hasContentDescription(getString(R.string.navigation_app_menu))
        )
            .performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNode(
            hasContentDescription(getString(R.string.go_to_help_and_feedback_screen))
        )
            .performClick()
        composeTestRule.waitForIdle()


        Screengrab.screenshot("about_screen_baseline")
    }

    @Test
    fun testMenuOnTopOfStartPageScreen() {
        composeTestRule.onNode(
            hasContentDescription(getInstrumentation().targetContext.getString(R.string.navigation_app_menu))
        ).performClick()
        composeTestRule.waitForIdle()

        Screengrab.screenshot("menu_on_top_of_start_page")
    }

    fun getString(@StringRes id: Int): String {
        return getInstrumentation().targetContext.getString(id)
    }
}