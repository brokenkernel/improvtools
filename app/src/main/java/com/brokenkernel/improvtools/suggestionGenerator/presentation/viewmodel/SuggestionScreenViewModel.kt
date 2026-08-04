package com.brokenkernel.improvtools.suggestionGenerator.presentation.viewmodel

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.brokenkernel.improvtools.settings.data.repository.SettingsRepository
import com.brokenkernel.improvtools.suggestionGenerator.data.repository.MergedAudienceSuggestionDatumRepository
import com.brokenkernel.improvtools.suggestions.data.storage.IdeaCategoryODS
import com.brokenkernel.improvtools.suggestions.data.storage.IdeaItemODS
import com.brokenkernel.improvtools.suggestions.data.storage.IdeaUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
internal class SuggestionScreenViewModel @Inject constructor(
    suggestionDatumRepository: MergedAudienceSuggestionDatumRepository,
    private val settingsRepository: SettingsRepository,
) :
    ViewModel() {

    // TODO: don't expose ODS to UI ...
    val internalCategoryDatum: SnapshotStateList<IdeaCategoryODS> =
        suggestionDatumRepository.getIdeaCategories().toMutableStateList()

    private val _categoryDatumToSuggestion: MutableMap<IdeaCategoryODS, MutableStateFlow<IdeaUIState>> =
        HashMap()
    val categoryDatumToSuggestion: Map<IdeaCategoryODS, StateFlow<IdeaUIState>>

    init {
        internalCategoryDatum.forEach { item ->
            val newIdea = item.ideas.random()
            _categoryDatumToSuggestion[item] = MutableStateFlow(
                IdeaUIState.fromStoredModel(newIdea),
            )
        }

        categoryDatumToSuggestion =
            _categoryDatumToSuggestion.mapValues { x -> x.value.asStateFlow() }
    }

    internal fun updateSuggestionXFor(ic: IdeaCategoryODS) {
        val legalNewWords: Set<IdeaItemODS> = run {
            val ui: IdeaUIState = _categoryDatumToSuggestion.getValue(ic).value
            ic.ideas - IdeaItemODS(ui.idea, ui.explanation)
        }

        _categoryDatumToSuggestion[ic]?.value = IdeaUIState.fromStoredModel(legalNewWords.random())
    }

    internal fun resetAllCategories() {
        _categoryDatumToSuggestion.keys.forEach { k ->
            this.updateSuggestionXFor(k)
        }
    }
}
