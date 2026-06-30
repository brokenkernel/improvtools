package com.brokenkernel.improvtools.encyclopaedia.android

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.compose.ui.test.onRoot
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.brokenkernel.improvtools.encyclopaedia.android.people.PeopleTab
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
internal class PeopleTabTest {

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
            PeopleTab()
        }
    }

    @Test
    fun testPeopleTabDoesFunction() {
        composeTestRule.onRoot()
            .assertExists()
            .assertIsDisplayed()
    }
}
