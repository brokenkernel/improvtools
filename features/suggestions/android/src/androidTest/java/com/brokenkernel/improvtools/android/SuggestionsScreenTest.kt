package com.brokenkernel.improvtools.android

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.brokenkernel.improvtools.suggestions.view.SuggestionsTab
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

// TODO: deal with injecting fake ThesaurusRepository ... right now all suggestions tests are broken
@HiltAndroidTest
class SuggestionsScreenTest {

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
            SuggestionsTab(
                goTo = {},
            )
        }
    }

    @Test
    fun testResetButtonShownOnSuggestionsScreen() {
        composeTestRule
            .onNodeWithStringId(R.string.suggestions_reset_all)
            .assertIsDisplayed()
    }
}
