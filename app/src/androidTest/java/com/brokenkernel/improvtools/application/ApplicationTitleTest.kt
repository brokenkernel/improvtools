package com.brokenkernel.improvtools.application

import androidx.annotation.StringRes
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.brokenkernel.improvtools.application.data.model.ImprovToolsAppState
import com.brokenkernel.improvtools.application.data.model.NavigableRoute
import com.brokenkernel.improvtools.application.data.model.NavigableScreens
import com.brokenkernel.improvtools.application.data.model.rememberImprovToolsAppState
import com.brokenkernel.improvtools.application.presentation.view.OuterContentForMasterScreen
import com.brokenkernel.improvtools.infrastructure.HiltComponentActitivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class ApplicationTitleTest {

    @get:Rule(order = 0)
    var hiltRule: HiltAndroidRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule:
        AndroidComposeTestRule<ActivityScenarioRule<HiltComponentActitivity>, HiltComponentActitivity> =
        createAndroidComposeRule<HiltComponentActitivity>()

    private lateinit var improvToolsState: ImprovToolsAppState

    @Before
    fun setupAppNavHost() {
        hiltRule.inject()
        composeTestRule.setContent {
            val titleState = remember { mutableStateOf(NavigableScreens.SuggestionGeneratorScreen.titleResource) }

            improvToolsState = rememberImprovToolsAppState(titleState = titleState)
            OuterContentForMasterScreen(improvToolsState, NavigableRoute.SuggestionGeneratorRoute)
        }
    }

    @Test
    fun testGamesTitleIsGames() {
        composeTestRule.onNodeWithTag(ApplicationConstants.APPLICATION_TITLE)
            .assertExists()
            .assertIsDisplayed()
            .assertTextEquals(getString(NavigableScreens.SuggestionGeneratorScreen.titleResource))

        // TODO: expose proper navigation functions
        getInstrumentation().runOnMainSync {
            improvToolsState.navigateTo(NavigableRoute.GamesPageRoute)
        }

        composeTestRule.onNodeWithTag(ApplicationConstants.APPLICATION_TITLE)
            .assertExists()
            .assertIsDisplayed()
            .assertTextEquals(getString(NavigableScreens.GamesPageScreen.titleResource))

        getInstrumentation().runOnMainSync {
            improvToolsState.navigateBack()
        }

        composeTestRule.onNodeWithTag(ApplicationConstants.APPLICATION_TITLE)
            .assertExists()
            .assertIsDisplayed()
            .assertTextEquals(getString(NavigableScreens.SuggestionGeneratorScreen.titleResource))
    }

    private fun getString(@StringRes id: Int): String {
        return getInstrumentation().targetContext.getString(id)
    }
}

// TODO: rename everything in acc' with high level naming guidance
