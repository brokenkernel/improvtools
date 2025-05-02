package com.brokenkernel.components.view

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HtmlTextTest {

    @get:Rule
    val composeTestRule: ComposeContentTestRule = createComposeRule()

    @Test
    fun testSimpleHtmlTextShowsText() {
        composeTestRule.setContent {
            MaterialTheme {
                HtmlText("Hallo")
            }
        }
        composeTestRule
            .onNodeWithText("Hallo")
            .assertExists()
            .assertIsDisplayed()
    }

    @Test
    fun testHtmlDoesUseHtmlAndNowRaw() {
        composeTestRule.setContent {
            MaterialTheme {
                HtmlText("<b>Hallo<b>")
            }
        }
        composeTestRule
            .onNodeWithText("<b>Hallo</b>")
            .assertDoesNotExist()
    }
}
