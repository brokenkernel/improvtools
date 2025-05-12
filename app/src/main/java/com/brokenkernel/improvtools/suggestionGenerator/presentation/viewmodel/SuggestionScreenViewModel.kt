package com.brokenkernel.improvtools.suggestionGenerator.presentation.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brokenkernel.improvtools.settings.data.repository.SettingsRepository
import com.brokenkernel.improvtools.suggestionGenerator.data.model.IdeaCategoryODS
import com.brokenkernel.improvtools.suggestionGenerator.data.model.IdeaItemODS
import com.brokenkernel.improvtools.suggestionGenerator.data.model.IdeaUIState
import com.brokenkernel.improvtools.suggestionGenerator.data.repository.MergedAudienceSuggestionDatumRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@HiltViewModel
internal class SuggestionScreenViewModel @Inject constructor(
    suggestionDatumRepository: MergedAudienceSuggestionDatumRepository,
    private val settingsRepository: SettingsRepository,
) :
    ViewModel() {
    private var shouldReuseSuggestions = MutableStateFlow(false)

    // TODO: don't expose ODS to UI ...
    val internalCategoryDatum: SnapshotStateList<IdeaCategoryODS> =
        suggestionDatumRepository.getIdeaCategories().toMutableStateList()

    private val _categoryDatumToSuggestion: MutableMap<IdeaCategoryODS, MutableStateFlow<IdeaUIState>> =
        HashMap<IdeaCategoryODS, MutableStateFlow<IdeaUIState>>()
    val categoryDatumToSuggestion: Map<IdeaCategoryODS, StateFlow<IdeaUIState>>

    init {
        viewModelScope.launch {
            settingsRepository.userSettingsFlow.collectLatest { it ->
                shouldReuseSuggestions.value = it.allowSuggestionsReuse
            }
        }
        internalCategoryDatum.forEach { item ->
            val newIdea = item.ideas.random()
            _categoryDatumToSuggestion[item] = MutableStateFlow(
                IdeaUIState.fromStoredModel(newIdea),
            )
        }

        categoryDatumToSuggestion = _categoryDatumToSuggestion.mapValues { x -> x.value.asStateFlow() }
    }

    internal fun updateSuggestionXFor(ic: IdeaCategoryODS) {
        val legalNewWords: Set<IdeaItemODS> = if (shouldReuseSuggestions.value) {
            ic.ideas
        } else {
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
