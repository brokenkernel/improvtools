package com.brokenkernel.components.view

import org.junit.Test

class StringUtilsTest {
    @Test
    fun testToTitleCaseWithNoChanges() {
        assert("Foo".toTitleCase() == "Foo")
    }

    @Test
    fun testToTitleCaseSingleWordCapital() {
        assert("foo".toTitleCase() == "Foo")
    }

    @Test
    fun testToTitleCaseTwoWordCapital() {
        assert("foo foo".toTitleCase() == "Foo Foo")
    }

    @Test
    fun testToTitleCaseEmptyString() {
        assert("".toTitleCase() == "")
    }

    @Test
    fun testSplitsAndJoins() {
        assert("a_b_c".toTitleCase("_", " ") == "A B C")
    }
}
