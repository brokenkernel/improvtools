package com.brokenkernel.improvtools.encyclopaedia

import androidx.annotation.StringRes
import androidx.navigation3.runtime.EntryProviderScope
import com.brokenkernel.improvtools.application.data.model.ImprovToolsAppState
import com.brokenkernel.improvtools.coreinfra.ImprovToolsNavigationKey
import com.brokenkernel.improvtools.encyclopaedia.android.api.EmotionsPageNavigationKey
import com.brokenkernel.improvtools.encyclopaedia.android.api.GamesPageNavigationKey
import com.brokenkernel.improvtools.encyclopaedia.android.api.GlossaryPageNavigationKey
import com.brokenkernel.improvtools.encyclopaedia.android.api.PeoplePageNavigationKey
import com.brokenkernel.improvtools.encyclopaedia.android.api.ThesaurusAllItemsPageNavigationKey
import com.brokenkernel.improvtools.encyclopaedia.android.api.ThesaurusSingleWordPageNavigationKey
import com.brokenkernel.improvtools.encyclopaedia.android.api.TipsAndAdviceNavigationKey
import com.brokenkernel.improvtools.encyclopaedia.presentation.view.ThesaurusTabAllItems
import com.brokenkernel.improvtools.encyclopaedia.presentation.view.ThesaurusTabSingleWord
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

internal fun EntryProviderScope<ImprovToolsNavigationKey>.encyclopaediaScreensEntryBuilder(
    navigator: DestinationsNavigator,
    improvToolsAppState: ImprovToolsAppState,
) {
    entry<TipsAndAdviceNavigationKey> {
        // TODO
    }
    entry<GamesPageNavigationKey> {
        // TODO
    }
    entry<PeoplePageNavigationKey> {
        // TODO
    }
    entry<GlossaryPageNavigationKey> {
        // TODO
    }
    entry<EmotionsPageNavigationKey> {
        // TODO
    }
    entry<ThesaurusAllItemsPageNavigationKey> {
        ThesaurusTabAllItems(
            improvToolsAppState = improvToolsAppState,
        )
    }
    entry<ThesaurusSingleWordPageNavigationKey> { route ->
        ThesaurusTabSingleWord(
            word = route.word,
            navigator = improvToolsAppState.navigator,
            priorTitleResource = route.priorTitleResource,
        )
    }
}
