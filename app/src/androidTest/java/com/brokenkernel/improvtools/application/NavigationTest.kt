package com.brokenkernel.improvtools.application

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onRoot
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.application.data.model.NavigableScreens
import com.brokenkernel.improvtools.application.presentation.view.OuterContentForMasterScreen
import com.brokenkernel.improvtools.infrastructure.HiltComponentActitivity
import com.brokenkernel.improvtools.infrastructure.onNodeWithStringId
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class NavigationTest {

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
            OuterContentForMasterScreen(
                NavigableScreens.SuggestionGeneratorScreen,
            )
        }
    }

    @Test
    fun testStartScreenIsSuggestions() {
        composeTestRule.onNodeWithTag("OutermostContentForSuggestionsScreen").assertIsDisplayed()
    }

    @Test
    fun testSomethingIsDisplayedOnRoot() {
        composeTestRule
            .onRoot()
            .assertIsDisplayed()
    }

    @Test
    fun testResetButtonShownOnSuggestionsScreen() {
        composeTestRule
            .onNodeWithStringId(R.string.suggestions_reset_all)
            .assertIsDisplayed()
    }
}
