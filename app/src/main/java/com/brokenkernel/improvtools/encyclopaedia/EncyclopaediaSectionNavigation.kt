package com.brokenkernel.improvtools.encyclopaedia

import com.brokenkernel.improvtools.application.data.model.ImprovToolsAppState
import com.ramcosta.composedestinations.generated.destinations.ThesaurusTabSingleWordDestination

internal object EncyclopaediaSectionNavigation {
    fun navigateToThesaurusWord(improvToolsAppState: ImprovToolsAppState, word: String) {
        improvToolsAppState.navigator.navigate(
            ThesaurusTabSingleWordDestination(word, improvToolsAppState.currentTitle.value),
        )
    }
}
