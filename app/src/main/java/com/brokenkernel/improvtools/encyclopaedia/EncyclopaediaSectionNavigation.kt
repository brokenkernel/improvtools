package com.brokenkernel.improvtools.encyclopaedia

import com.brokenkernel.improvtools.application.data.model.ImprovToolsAppState
import com.brokenkernel.improvtools.application.data.model.NavigableRoute
import com.brokenkernel.improvtools.application.data.model.NavigableScreens

// TODO: consider some base class that every section implements?
internal object EncyclopaediaSectionNavigation {
    fun navigateBackToThesaurus(improvToolsAppState: ImprovToolsAppState) {
        improvToolsAppState.navigateBackTo(
            NavigableRoute.ThesaurusPageRoute,
            NavigableScreens.ThesaurusPageScreen.titleResource,
        )
    }

    fun navigateToThesaurusWord(improvToolsAppState: ImprovToolsAppState, word: String) {
        improvToolsAppState.navigateTo(
            NavigableRoute.ThesaurusWordRoute(word),
            NavigableScreens.ThesaurusPageScreen.titleResource,
        )
    }
}
