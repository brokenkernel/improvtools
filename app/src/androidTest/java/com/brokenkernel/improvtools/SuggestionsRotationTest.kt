package com.brokenkernel.improvtools

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onRoot
import androidx.test.espresso.device.DeviceInteraction.Companion.setScreenOrientation
import androidx.test.espresso.device.EspressoDevice.Companion.onDevice
import androidx.test.espresso.device.action.ScreenOrientation
import androidx.test.espresso.device.rules.ScreenOrientationRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.brokenkernel.improvtools.infrastructure.onNodeWithStringId
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class SuggestionsRotationTest {

    @get:Rule(order = 0)
    var hiltRule: HiltAndroidRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity> =
        createAndroidComposeRule<MainActivity>()

    @get:Rule(order = 2)
    val screenOrientationRule: ScreenOrientationRule =
        ScreenOrientationRule(ScreenOrientation.PORTRAIT)

    @Before
    fun setupAppNavHost() {
        hiltRule.inject()
    }

    @Test
    fun assertRootStillDisplayedAfterRotation() {
        onDevice().setScreenOrientation(ScreenOrientation.LANDSCAPE)
        composeTestRule
            .onRoot()
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithStringId(R.string.suggestions_reset_all)
            .assertIsDisplayed()
    }
}
