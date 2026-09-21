package com.brokenkernel.improvtools.encyclopaedia

import androidx.annotation.StringRes
import androidx.navigation3.runtime.EntryProviderScope
import com.brokenkernel.improvtools.application.data.model.ImprovToolsAppState
import com.brokenkernel.improvtools.coreinfra.BackStack
import com.brokenkernel.improvtools.coreinfra.ImprovToolsNavigationKey
import com.brokenkernel.improvtools.encyclopaedia.android.api.EmotionsPageNavigationKey
import com.brokenkernel.improvtools.encyclopaedia.android.api.GamesPageNavigationKey
import com.brokenkernel.improvtools.encyclopaedia.android.api.GlossaryPageNavigationKey
import com.brokenkernel.improvtools.encyclopaedia.android.api.PeoplePageNavigationKey
import com.brokenkernel.improvtools.encyclopaedia.android.api.ThesaurusAllItemsPageNavigationKey
import com.brokenkernel.improvtools.encyclopaedia.android.api.ThesaurusSingleWordPageNavigationKey
import com.brokenkernel.improvtools.encyclopaedia.android.api.TipsAndAdviceNavigationKey
import com.brokenkernel.improvtools.encyclopaedia.android.emotions.EmotionsTab
import com.brokenkernel.improvtools.encyclopaedia.android.glossary.GlossaryTab
import com.brokenkernel.improvtools.encyclopaedia.android.people.PeopleTab
import com.brokenkernel.improvtools.encyclopaedia.presentation.view.GamesTab
import com.brokenkernel.improvtools.encyclopaedia.presentation.view.ThesaurusTabAllItems
import com.brokenkernel.improvtools.encyclopaedia.presentation.view.ThesaurusTabSingleWord
import com.brokenkernel.improvtools.encyclopaedia.presentation.view.TipsAndAdviceTab

internal object EncyclopaediaSectionNavigation {
    fun navigateToThesaurusWord(
        backstack: BackStack,
        word: String,
        @StringRes priorTitleRes: Int,
    ) {
        backstack.add(ThesaurusSingleWordPageNavigationKey(word, priorTitleRes))
    }
}

internal fun EntryProviderScope<ImprovToolsNavigationKey>.encyclopaediaScreensEntryBuilder(
    backstack: BackStack,
    improvToolsAppState: ImprovToolsAppState,
) {
    entry<TipsAndAdviceNavigationKey> {
        TipsAndAdviceTab()
    }
    entry<GamesPageNavigationKey> {
        GamesTab(
            backstack = backstack,
        )
    }
    entry<PeoplePageNavigationKey> {
        PeopleTab()
    }
    entry<GlossaryPageNavigationKey> {
        GlossaryTab()
    }
    entry<EmotionsPageNavigationKey> {
        EmotionsTab()
    }
    entry<ThesaurusAllItemsPageNavigationKey> {
        ThesaurusTabAllItems(
            backstack = backstack,
            improvToolsAppState = improvToolsAppState,
        )
    }
    entry<ThesaurusSingleWordPageNavigationKey> { route ->
        ThesaurusTabSingleWord(
            backstack = backstack,
            word = route.word,
            priorTitleResource = route.priorTitleResource,
        )
    }
}
