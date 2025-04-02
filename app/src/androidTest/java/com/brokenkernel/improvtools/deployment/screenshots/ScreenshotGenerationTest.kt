package com.brokenkernel.improvtools.deployment.screenshots

import androidx.annotation.StringRes
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import androidx.lifecycle.Lifecycle
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.brokenkernel.improvtools.MainActivity
import com.brokenkernel.improvtools.OuterContentForMasterScreen
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.application.data.model.NavigableRoute
import com.brokenkernel.improvtools.application.presentation.view.DrawerNavGraph
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import tools.fastlane.screengrab.Screengrab
import tools.fastlane.screengrab.UiAutomatorScreenshotStrategy

@RunWith(AndroidJUnit4::class)
class ScreenshotGenerationTest {


    @get:Rule(order = 1)
    val composeTestRule: ComposeContentTestRule = createComposeRule()

//    lateinit var navController: TestNavHostController
//
//    @get:Rule
//    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setupAppNavHost() {
        Screengrab.setDefaultScreenshotStrategy(UiAutomatorScreenshotStrategy())

        composeTestRule.setContent {
//            navController = TestNavHostController(LocalContext.current)
//            navController.navigatorProvider.addNavigator(ComposeNavigator())
//            DrawerNavGraph(drawerNavController = navController)
            OuterContentForMasterScreen()
        }

    }
//
//    @Test
//    fun takeSuggestionsPageScreenshot() {
//        activityScenarioRule.scenario.onActivity { activity ->
//            navController.navigate(NavigableRoute.SuggestionGeneratorRoute)
//            Screengrab.setDefaultScreenshotStrategy(UiAutomatorScreenshotStrategy())
//            Screengrab.screenshot("suggestionGenerator_screen_baseline")
//        }
//    }
//
//    @Test
//    fun takeSettingsPageScreenshot() {
//        activityScenarioRule.scenario.onActivity { activity ->
//            navController.navigate(NavigableRoute.SettingsRoute)
//            Screengrab.setDefaultScreenshotStrategy(UiAutomatorScreenshotStrategy())
//            Screengrab.screenshot("settings_screen_baseline")
//        }
//    }
//
//    @Test
//    fun takeTimerPageScreenshot() {
//        activityScenarioRule.scenario.onActivity { activity ->
//            navController.navigate(NavigableRoute.TimerRoute)
//            Screengrab.setDefaultScreenshotStrategy(UiAutomatorScreenshotStrategy())
//            Screengrab.screenshot("timer_screen_baseline")
//        }
//    }

    @Test
    fun testMenuOnTopOfStartPageScreen() {
        composeTestRule.onNode(
            hasContentDescription(getInstrumentation().targetContext.getString(R.string.navigation_app_menu))
        ).performClick()
        composeTestRule.waitForIdle()
        Screengrab.setDefaultScreenshotStrategy(UiAutomatorScreenshotStrategy())
        Screengrab.screenshot("menu_on_top_of_start_page")
    }

    @Test
    fun takeAboutPageScreenshot() {
        composeTestRule.onNode(
            hasContentDescription(getString(R.string.navigation_app_menu)))
            .performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNode(
            hasContentDescription(getString(R.string.go_to_help_and_feedback_screen)))
            .performClick()
        composeTestRule.waitForIdle()


        Screengrab.screenshot("about_screen_baseline")
    }

    fun getString(@StringRes id: Int): String {
        return getInstrumentation().targetContext.getString(id)
    }
}