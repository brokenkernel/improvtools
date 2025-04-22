package com.brokenkernel.improvtools.suggestionGenerator.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brokenkernel.improvtools.encyclopaedia.api.ThesaurusAPI
import com.brokenkernel.improvtools.settings.data.repository.SettingsRepository
import com.brokenkernel.improvtools.suggestionGenerator.data.model.IdeaCategory
import com.brokenkernel.improvtools.suggestionGenerator.data.model.IdeaItem
import com.brokenkernel.improvtools.suggestionGenerator.data.repository.AudienceSuggestionDatumRepository
import com.brokenkernel.improvtools.suggestionGenerator.data.repository.MergedAudienceSuggestionDatumRepository
import com.brokenkernel.improvtools.suggestionGenerator.presentation.uistate.SuggestionScreenUIState
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
    private val _uiState = MutableStateFlow(SuggestionScreenUIState.default())

    // TODO: figure out a nicer way to get suggestions from other places. I don't need everything in json, but some should be, and perhaps in the future local GenAI (hey: real use for it :-)
    // in the meantime, whatever. Also tests!

    val internalCategoryDatum: List<IdeaCategory> = suggestionDatumRepository.getIdeaCategories()
    val _categoryDatumToSuggestion: MutableMap<IdeaCategory, MutableStateFlow<String>> =
        HashMap<IdeaCategory, MutableStateFlow<String>>()
    val categoryDatumToSuggestion: Map<IdeaCategory, StateFlow<String>>

    init {
        viewModelScope.launch {
            settingsRepository.userSettingsFlow.collectLatest { it ->
                _uiState.value =
                    _uiState.value.copy(shouldReuseSuggestions = it.allowSuggestionsReuse)
            }
        }
        internalCategoryDatum.forEach { item ->
            _categoryDatumToSuggestion[item] = MutableStateFlow(item.ideas.random().idea)
        }

        categoryDatumToSuggestion = _categoryDatumToSuggestion.mapValues { x -> x.value.asStateFlow() }
    }

    internal fun updateSuggestionXFor(ic: IdeaCategory) {
        val legalNewWords: Set<String> = if (_uiState.value.shouldReuseSuggestions) {
            ic.ideas.map { x -> x.idea }.toSet()
        } else {
            ic.ideas.map { x -> x.idea }.toSet() - _categoryDatumToSuggestion[ic]?.value.orEmpty()
        }

        _categoryDatumToSuggestion[ic]?.value = legalNewWords.random()
    }

    internal fun resetAllCategories() {
        _categoryDatumToSuggestion.keys.forEach { k ->
            this.updateSuggestionXFor(k)
        }
    }
}
