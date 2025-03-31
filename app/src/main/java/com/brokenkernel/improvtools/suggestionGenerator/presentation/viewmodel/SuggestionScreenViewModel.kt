package com.brokenkernel.improvtools.suggestionGenerator.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.brokenkernel.improvtools.ImprovToolsApplication
import com.brokenkernel.improvtools.settings.data.repository.SettingsRepository
import com.brokenkernel.improvtools.suggestionGenerator.data.model.SuggestionCategory
import com.brokenkernel.improvtools.suggestionGenerator.data.repository.AudienceSuggestionDatumRepository
import com.brokenkernel.improvtools.suggestionGenerator.presentation.uistate.SuggestionScreenUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.EnumMap

internal class SuggestionScreenViewModel(
    private val suggestionDatumRepository: AudienceSuggestionDatumRepository,
    private val settingsRepository: SettingsRepository,
) :
    ViewModel() {
    private val _uiState = MutableStateFlow(SuggestionScreenUIState())
    internal val uiState: StateFlow<SuggestionScreenUIState> = _uiState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)

    // TODO: move this to a LoadableScreen Compsable
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
//            xmlResource
//            val context = LocalContext.current
//            val resources = context.resources
//            val audienceDatumAsXML = resources.getXml(R.xml.audience_suggestion_datum)


//            delay(2000) // TODO temporary example for myself
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
        val suggestionIdeasForCategory: Set<String> =
            suggestionDatumRepository.getAudienceDatumForCategory(suggestionCategory)
        return (suggestionIdeasForCategory - currentWord).random()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as ImprovToolsApplication)
                val suggestionsDatumRepository =
                    application.container.audienceSugestionDatumRespository
                val settingsRepository =
                    application.container.settingsRepository

                SuggestionScreenViewModel(suggestionsDatumRepository, settingsRepository)
            }
        }
    }

}
