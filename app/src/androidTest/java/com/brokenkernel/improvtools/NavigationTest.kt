package com.brokenkernel.improvtools

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.navigation.NavController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.brokenkernel.improvtools.application.data.model.NavigableScreens
import com.brokenkernel.improvtools.application.presentation.view.DrawerNavGraph
import com.brokenkernel.improvtools.infrastructure.HiltComponentActitivity
import com.brokenkernel.improvtools.infrastructure.assertCurrentNavigableScreen
import com.brokenkernel.improvtools.infrastructure.onNodeWithStringId
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.*

@HiltAndroidTest
class NavigationTest {

    @get:Rule(order = 0)
    var hiltRule: HiltAndroidRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltComponentActitivity>()

    lateinit var navController: TestNavHostController

    @Before
    fun setupAppNavHost() {
        hiltRule.inject()
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            DrawerNavGraph(drawerNavController = navController)
        }
    }

    @Test
    fun testStartScreenIsSuggestions() {
        navController.assertCurrentNavigableScreen(NavigableScreens.SuggestionGenerator)
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

    // todo: test drawer is closed??
}