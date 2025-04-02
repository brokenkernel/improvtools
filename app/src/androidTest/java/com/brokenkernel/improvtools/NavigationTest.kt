package com.brokenkernel.improvtools

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.brokenkernel.improvtools.application.presentation.view.DrawerNavGraph
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavigationTest {
    @get:Rule
    val composeTestRule: ComposeContentTestRule = createComposeRule()
    lateinit var navController: TestNavHostController

    @Before
    fun setupAppNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            DrawerNavGraph(drawerNavController = navController)
        }
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
            .onNodeWithText("Reset All")
            .assertIsDisplayed()
    }

    // todo: test drawer is closed??
}