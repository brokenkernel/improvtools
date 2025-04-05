package com.brokenkernel.improvtools.tipsandadvice.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.tipsandadvice.data.model.TipsAndAdviceOnUIModel
import com.brokenkernel.improvtools.tipsandadvice.data.model.TipsAndAdviceProcessedModel
import com.brokenkernel.improvtools.tipsandadvice.data.repository.TipsAndAdviceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.io.InputStream


@OptIn(ExperimentalSerializationApi::class)
@HiltViewModel
internal class TipsAndAdviceViewModel @Inject constructor(tipsAndAdviceRepository: TipsAndAdviceRepository) :
    ViewModel() {

    // must happen inside of init block since we don't want blank default
    private val _uiState: MutableStateFlow<TipsAndAdviceOnUIModel>
    val uiState: StateFlow<TipsAndAdviceOnUIModel>

    private val tipsAndAdviceProcessed: Iterator<Pair<String, String>>

    init {
        val unprocessedTipsAndAdvice: InputStream =
            tipsAndAdviceRepository.resources.openRawResource(R.raw.tips_and_advice)
        val tipsAndAdviceDatum: TipsAndAdviceProcessedModel =
            Json.decodeFromStream<TipsAndAdviceProcessedModel>(unprocessedTipsAndAdvice)

        val rawDictTipsAndAdvice = tipsAndAdviceDatum
        tipsAndAdviceProcessed = rawDictTipsAndAdvice.asInfiniteSequence().iterator()
        _uiState = MutableStateFlow(getNewlTOTD())
        uiState = _uiState.asStateFlow()
    }

    fun getNewlTOTD(): TipsAndAdviceOnUIModel {
        if (tipsAndAdviceProcessed.hasNext()) {
            val rawEntry: Pair<String, String> = tipsAndAdviceProcessed.next()
            return TipsAndAdviceOnUIModel(rawEntry.first, rawEntry.second)
        } else {
            // This should never happen but we can't assert it.
            // TODO: This should really have failed in init but we can't deal with that yet.
            return TipsAndAdviceOnUIModel.getDefault()
        }
    }

    fun loadNewTOTD() {
        _uiState.value = getNewlTOTD()
    }
}