package com.brokenkernel.improvtools.suggestionGenerator.presentation.viewmodel

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.suggestionGenerator.data.model.SuggestionCategory
import com.brokenkernel.improvtools.suggestionGenerator.presentation.uistate.SuggestionScreenUIState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.EnumMap

class SuggestionScreenViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SuggestionScreenUIState())
    internal val uiState: StateFlow<SuggestionScreenUIState> = _uiState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
        .onStart {
             loadAudienceSuggestionDatum()
            emit(_isLoading.value)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = false
        )

    fun loadAudienceSuggestionDatum(): Unit {
        viewModelScope.launch {
            _isLoading.value = true
            delay(2000) // TODO temporary example for myself
            _isLoading.value = false
        }
    }

    init {
        resetAllCategories()
    }

    internal fun updateSuggestionFor(suggestionCategory: SuggestionCategory): Unit {
        _uiState.value = SuggestionScreenUIState(
            audienceSuggestions = _uiState.value.audienceSuggestions + mapOf(
                suggestionCategory to pickAnotherItemFromCategoryDatum(
                    suggestionCategory,
                    _uiState.value.audienceSuggestions.getValue(suggestionCategory)
                )
            )
        )
    }

    internal fun resetAllCategories(): Unit {
        val audienceSuggestions: MutableMap<SuggestionCategory, String> =
            EnumMap(SuggestionCategory::class.java)

        SuggestionCategory.entries.forEach { item ->
            val word = pickAnotherItemFromCategoryDatum(
                item, _uiState.value.audienceSuggestions.getOrDefault(
                    item,
                    ""
                )
            )
            audienceSuggestions[item] = word
        }
        _uiState.value = SuggestionScreenUIState(audienceSuggestions = audienceSuggestions)

    }

    /**
     * Pick a word that differs from the existing word.
     */
    private fun pickAnotherItemFromCategoryDatum(
        suggestionCategory: SuggestionCategory,
        currentWord: String,
    ): String {
        return (suggestionCategory.ideas - currentWord).random()
    }

    init {
        val context = LocalContext.current
        val resources = context.resources
        val audienceDatumAsXML = resources.getXml(R.xml.audience_suggestion_datum)
    }

}
