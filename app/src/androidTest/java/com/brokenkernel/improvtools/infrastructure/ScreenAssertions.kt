package com.brokenkernel.improvtools.infrastructure

import androidx.navigation.NavController
import com.brokenkernel.improvtools.application.data.model.NavigableScreens
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers

internal fun NavController.assertCurrentNavigableScreen(expectedRoute: NavigableScreens) {
    assertThat(
        currentBackStackEntry?.destination?.route,
        Matchers.`is`(expectedRoute.route::class.qualifiedName)
    )
}
