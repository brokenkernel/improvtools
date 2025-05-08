package com.brokenkernel.improvtools.encyclopaedia.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.datastore.UserSettings
import com.brokenkernel.improvtools.encyclopaedia.data.model.TipContentUI
import com.brokenkernel.improvtools.encyclopaedia.data.model.TipsAndAdviceProcessedModel
import com.brokenkernel.improvtools.encyclopaedia.data.model.TipsAndAdviceUI
import com.brokenkernel.improvtools.encyclopaedia.data.model.TipsAndAdviceViewModeUI
import com.brokenkernel.improvtools.encyclopaedia.data.repository.TipsAndAdviceRepository
import com.brokenkernel.improvtools.settings.data.repository.SettingsRepository
import com.typesafe.config.ConfigFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import java.io.InputStream
import java.io.InputStreamReader
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.hocon.Hocon
import kotlinx.serialization.hocon.decodeFromConfig

@OptIn(ExperimentalSerializationApi::class)
@HiltViewModel
internal class TipsAndAdviceViewModel @Inject constructor(
    tipsAndAdviceRepository: TipsAndAdviceRepository,
    settingsRepository: SettingsRepository,
) :
    ViewModel() {

    // must happen inside of init block since we don't want blank default
    private val _uiState: MutableStateFlow<TipsAndAdviceUI>
    val uiState: StateFlow<TipsAndAdviceUI>

    private val _taaViewMode: MutableStateFlow<TipsAndAdviceViewModeUI> = MutableStateFlow(
        TipsAndAdviceViewModeUI.Companion.byInternalEnumValue(
            UserSettings.TipsAndTricksViewMode.VIEW_MODE_DEFAULT,
        ),
    )
    val taaViewMode: StateFlow<TipsAndAdviceViewModeUI> = _taaViewMode.asStateFlow()

    private val tipsAndAdviceProcessed: List<TipContentUI>

    init {
        // TODO: pull out  into repository
        val unprocessedTipsAndAdvice: InputStream =
            tipsAndAdviceRepository.resources.openRawResource(R.raw.tips_and_advice)
        val irs = InputStreamReader(unprocessedTipsAndAdvice)
        val conf = ConfigFactory.parseReader(irs)
        val tipsAndAdviceDatum: TipsAndAdviceProcessedModel =
            Hocon.decodeFromConfig<TipsAndAdviceProcessedModel>(conf)

        val rawDictTipsAndAdvice = tipsAndAdviceDatum
        tipsAndAdviceProcessed = rawDictTipsAndAdvice.advice.toList().map { (x, y) -> TipContentUI(x, y) }
        _uiState = MutableStateFlow(TipsAndAdviceUI(tipsAndAdviceProcessed))
        uiState = _uiState.asStateFlow()
        viewModelScope.launch {
            settingsRepository.userSettingsFlow.collectLatest { it ->
                _taaViewMode.value = TipsAndAdviceViewModeUI.Companion.byInternalEnumValue(it.tipsAndTricksViewMode)
            }
        }
    }
}
