package com.brokenkernel.improvtools.encyclopaedia

import com.brokenkernel.improvtools.application.data.model.ImprovToolsAppState
import com.brokenkernel.improvtools.application.data.model.NavigableRoute

// TODO: consider some base class that every section implements?
internal object EncyclopaediaSectionNavigation {
    fun navigateToThesaurusWord(improvToolsAppState: ImprovToolsAppState, word: String) {
        improvToolsAppState.navigateTo(
            NavigableRoute.ThesaurusWordRoute(word),
        )
    }
}
