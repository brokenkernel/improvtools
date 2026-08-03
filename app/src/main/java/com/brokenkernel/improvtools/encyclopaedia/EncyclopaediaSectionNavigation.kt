package com.brokenkernel.improvtools.encyclopaedia

import androidx.annotation.StringRes
import com.ramcosta.composedestinations.generated.app.destinations.ThesaurusTabSingleWordDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

internal object EncyclopaediaSectionNavigation {
    fun navigateToThesaurusWord(
        navigator: DestinationsNavigator,
        word: String,
        @StringRes priorTitleRes: Int,
    ) {
        navigator.navigate(
            ThesaurusTabSingleWordDestination(word, priorTitleRes),
        )
    }
}
