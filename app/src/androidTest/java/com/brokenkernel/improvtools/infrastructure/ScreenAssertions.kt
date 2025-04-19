package com.brokenkernel.improvtools.infrastructure

import androidx.navigation.NavController
import com.brokenkernel.improvtools.application.data.model.NavigableRoute
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers

internal fun NavController.assertCurrentNavigableRoute(expectedRoute: NavigableRoute) {
    assertThat(
        currentBackStackEntry?.destination?.route,
        Matchers.`is`(expectedRoute::class.qualifiedName),
    )
}
