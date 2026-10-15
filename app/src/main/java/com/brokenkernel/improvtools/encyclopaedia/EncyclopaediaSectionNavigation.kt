package com.brokenkernel.improvtools.encyclopaedia

import androidx.navigation3.runtime.EntryProviderScope
import com.brokenkernel.improvtools.coreinfra.ImprovToolsNavigationKey
import com.brokenkernel.improvtools.coreinfra.ImprovToolsNavigator
import com.brokenkernel.improvtools.encyclopaedia.android.api.EmotionsPageNavigationKey
import com.brokenkernel.improvtools.encyclopaedia.android.api.GlossaryPageNavigationKey
import com.brokenkernel.improvtools.encyclopaedia.android.api.PeoplePageNavigationKey
import com.brokenkernel.improvtools.encyclopaedia.android.api.ThesaurusAllItemsPageNavigationKey
import com.brokenkernel.improvtools.encyclopaedia.android.api.ThesaurusSingleWordPageNavigationKey
import com.brokenkernel.improvtools.encyclopaedia.android.api.TipsAndAdviceNavigationKey
import com.brokenkernel.improvtools.encyclopaedia.android.emotions.EmotionsTab
import com.brokenkernel.improvtools.encyclopaedia.android.glossary.view.GlossaryTab
import com.brokenkernel.improvtools.encyclopaedia.android.people.PeopleTab
import com.brokenkernel.improvtools.encyclopaedia.presentation.view.ThesaurusTabAllItems
import com.brokenkernel.improvtools.encyclopaedia.presentation.view.ThesaurusTabSingleWord
import com.brokenkernel.improvtools.encyclopaedia.presentation.view.TipsAndAdviceTab

internal fun EntryProviderScope<ImprovToolsNavigationKey>.encyclopaediaScreensEntryBuilder(
    improvToolsNavigator: ImprovToolsNavigator,
) {
    entry<TipsAndAdviceNavigationKey> {
        TipsAndAdviceTab()
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
            improvToolsNavigator = improvToolsNavigator,
        )
    }
    entry<ThesaurusSingleWordPageNavigationKey> { route ->
        ThesaurusTabSingleWord(
            goBack = improvToolsNavigator::goBack,
            word = route.word,
            priorTitleResource = route.priorTitleResource,
        )
    }
}
