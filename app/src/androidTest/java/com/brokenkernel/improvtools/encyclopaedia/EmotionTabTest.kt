package com.brokenkernel.improvtools.encyclopaedia

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onRoot
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.brokenkernel.improvtools.encyclopaedia.presentation.view.EmotionTab
import com.brokenkernel.improvtools.infrastructure.HiltComponentActitivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class EmotionTabTest {

    @get:Rule(order = 0)
    var hiltRule: HiltAndroidRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule:
        AndroidComposeTestRule<ActivityScenarioRule<HiltComponentActitivity>, HiltComponentActitivity> =
        createAndroidComposeRule<HiltComponentActitivity>()

    @Before
    fun setupAppNavHost() {
        hiltRule.inject()
        composeTestRule.setContent {
            EmotionTab(
                onLaunchTitleCallback = { },
            )
        }
    }

    @Test
    fun testEmotionTabDoesFunction() {
        composeTestRule.onRoot()
            .assertExists()
            .assertIsDisplayed()
    }
}
