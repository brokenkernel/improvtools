package com.brokenkernel.improvtools.infrastructure

import androidx.navigation.NavController
import com.brokenkernel.improvtools.application.data.model.NavigableScreens
import org.junit.Assert.assertEquals

internal fun NavController.assertCurrentNavigableScreen(expectedRoute: NavigableScreens) {
    assertEquals(expectedRoute.route::class.qualifiedName, currentBackStackEntry?.destination?.route)
}
