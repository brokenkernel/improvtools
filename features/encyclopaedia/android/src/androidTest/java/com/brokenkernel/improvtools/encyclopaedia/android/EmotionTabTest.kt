package com.brokenkernel.improvtools.encyclopaedia.android

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.compose.ui.test.onRoot
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.brokenkernel.improvtools.encyclopaedia.android.emotions.EmotionTab
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
internal class EmotionTabTest {

    @get:Rule(order = 0)
    var hiltRule: HiltAndroidRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule:
        AndroidComposeTestRule<ActivityScenarioRule<XHiltComponentActivity>, XHiltComponentActivity> =
        createAndroidComposeRule<XHiltComponentActivity>()

    @Before
    fun setupAppNavHost() {
        hiltRule.inject()
        composeTestRule.setContent {
            EmotionTab()
        }
    }

    @Test
    fun testEmotionTabDoesFunction() {
        composeTestRule.onRoot()
            .assertExists()
            .assertIsDisplayed()
    }
}
