package com.brokenkernel.improvtools.encyclopaedia.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.datastore.UserSettings
import com.brokenkernel.improvtools.encyclopaedia.data.model.TipContentUIModel
import com.brokenkernel.improvtools.encyclopaedia.data.model.TipsAndAdviceOnUIModel
import com.brokenkernel.improvtools.encyclopaedia.data.model.TipsAndAdviceProcessedModel
import com.brokenkernel.improvtools.encyclopaedia.data.model.TipsAndAdviceViewModeUI
import com.brokenkernel.improvtools.encyclopaedia.data.repository.TipsAndAdviceRepository
import com.brokenkernel.improvtools.settings.data.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.io.InputStream

@OptIn(ExperimentalSerializationApi::class)
@HiltViewModel
internal class TipsAndAdviceViewModel @Inject constructor(
    tipsAndAdviceRepository: TipsAndAdviceRepository,
    settingsRepository: SettingsRepository,
) :
    ViewModel() {

    // must happen inside of init block since we don't want blank default
    private val _uiState: MutableStateFlow<TipsAndAdviceOnUIModel>
    val uiState: StateFlow<TipsAndAdviceOnUIModel>

    private val _taaViewMode: MutableStateFlow<TipsAndAdviceViewModeUI> = MutableStateFlow(
        TipsAndAdviceViewModeUI.Companion.byInternalEnumValue(
            UserSettings.TipsAndTricksViewMode.VIEW_MODE_DEFAULT,
        ),
    )
    val taaViewMode: StateFlow<TipsAndAdviceViewModeUI> = _taaViewMode.asStateFlow()

    private val tipsAndAdviceProcessed: List<TipContentUIModel>

    init {
        val unprocessedTipsAndAdvice: InputStream =
            tipsAndAdviceRepository.resources.openRawResource(R.raw.tips_and_advice)
        val tipsAndAdviceDatum: TipsAndAdviceProcessedModel =
            Json.Default.decodeFromStream<TipsAndAdviceProcessedModel>(unprocessedTipsAndAdvice)

        val rawDictTipsAndAdvice = tipsAndAdviceDatum
        tipsAndAdviceProcessed = rawDictTipsAndAdvice.advice.toList().map { (x, y) -> TipContentUIModel(x, y) }
        _uiState = MutableStateFlow(TipsAndAdviceOnUIModel(tipsAndAdviceProcessed))
        uiState = _uiState.asStateFlow()
        viewModelScope.launch {
            settingsRepository.userSettingsFlow.collectLatest { it ->
                _taaViewMode.value = TipsAndAdviceViewModeUI.Companion.byInternalEnumValue(it.tipsAndTricksViewMode)
            }
        }
    }
}
