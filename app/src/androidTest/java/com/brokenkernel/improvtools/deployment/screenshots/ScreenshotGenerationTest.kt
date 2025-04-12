package com.brokenkernel.improvtools.deployment.screenshots

import androidx.annotation.StringRes
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.application.data.model.NavigableScreens
import com.brokenkernel.improvtools.application.data.model.rememberImprovToolsAppState
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
    val composeTestRule: ComposeContentTestRule = createAndroidComposeRule<HiltComponentActitivity>()

    @Rule(order = 1)
    @JvmField
    val localeTestRule: LocaleTestRule = LocaleTestRule()

    @Before
    fun setupAppNavHost() {
        hiltRule.inject()
        Screengrab.setDefaultScreenshotStrategy(UiAutomatorScreenshotStrategy())

        composeTestRule.setContent {
            val improvToolsState = rememberImprovToolsAppState()
            OuterContentForMasterScreen(improvToolsState)
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
internal class ScreenshotGenerationTest : BaseScreenshotGenerationTest() {
    @Test
    fun testMenuOnTopOfStartPageScreen() {
        composeTestRule.onNode(
            hasContentDescription(getInstrumentation().targetContext.getString(R.string.navigation_app_menu))
        ).performClick()
        composeTestRule.waitForIdle()

        Screengrab.screenshot("menu_on_top_of_start_page")
    }
}


@HiltAndroidTest
@RunWith(Parameterized::class)
internal class ScreenshotGeneralPerNavigableScreenTest(
    private val navigableScreen: NavigableScreens,
) : BaseScreenshotGenerationTest() {

    companion object {
        @JvmStatic
        @Parameterized.Parameters()
        fun data(): Iterable<Array<Any>> {
            return arrayListOf(
                arrayOf(NavigableScreens.HelpAndAbout),
                arrayOf(NavigableScreens.Settings),
                arrayOf(NavigableScreens.SuggestionGenerator),
                arrayOf(NavigableScreens.Timer),
                arrayOf(NavigableScreens.TipsAndAdvice),
                arrayOf(NavigableScreens.WorkshopGenerator),
                arrayOf(NavigableScreens.Encyclopaedia),
            )
        }
    }


    @Test
    fun takeScreenshotOfNavigableScreen() {
        composeTestRule.onNode(
            hasContentDescription(getString(R.string.navigation_app_menu))
        )
            .performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNode(
            hasContentDescription(getString(navigableScreen.contentDescription))
        )
            .performClick()
        composeTestRule.waitForIdle()

        Screengrab.screenshot(navigableScreen.route::class.simpleName + "_screen_baseline")

    }
}