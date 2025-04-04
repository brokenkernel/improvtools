package com.brokenkernel.improvtools.tipsandadvice.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.tipsandadvice.data.model.TipsAndAdviceOnUIModel
import com.brokenkernel.improvtools.tipsandadvice.data.model.TipsAndAdviceProcessedModel
import com.brokenkernel.improvtools.tipsandadvice.data.repository.TipsAndAdviceRepository
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.InputStream


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
        val mapper = jacksonObjectMapper()
        mapper.configure(
            JsonParser.Feature.INCLUDE_SOURCE_IN_LOCATION, true
        )
        mapper.configure(
            JsonParser.Feature.STRICT_DUPLICATE_DETECTION, true
        )

        val tipsAndAdviceDatum: TipsAndAdviceProcessedModel? = mapper.readValue<TipsAndAdviceProcessedModel>(
            unprocessedTipsAndAdvice,
            TipsAndAdviceProcessedModel::class.java
        )
        val rawDictTipsAndAdvice = tipsAndAdviceDatum ?: TipsAndAdviceProcessedModel.getDefault()
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