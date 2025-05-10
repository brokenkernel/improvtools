package com.brokenkernel.improvtools.encyclopaedia.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brokenkernel.improvtools.TAG
import com.brokenkernel.improvtools.encyclopaedia.api.ThesaurusAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class LoadableSingleWordThesaurusButtonViewModel @Inject constructor(
    val thesaurusAPI: ThesaurusAPI, // todo: this should be accessed through a repository
) : ViewModel() {

    // TODO: expose a way to have a disposeEffect to cleanup. Otherwise there is a memory leak to some extent
    // (though it may also be a pre-cache 'optimisation')
    private val doesHaveDictionaryDetails: MutableMap<String, MutableStateFlow<Boolean>> = mutableMapOf()
    private val doesHaveDictionaryDetailsStateFlow: MutableMap<String, StateFlow<Boolean>> = mutableMapOf()

    fun doesHaveDictionaryDetails(word: String): StateFlow<Boolean> {
        val msf = doesHaveDictionaryDetails.computeIfAbsent(word, { MutableStateFlow<Boolean>(false) })
        val sf = doesHaveDictionaryDetailsStateFlow.computeIfAbsent(word, { k: String -> msf.asStateFlow() })
        val result = sf
        asyncInitWord(word)
        return result
    }

    private fun asyncInitWord(word: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = thesaurusAPI.hasWordDetails(word)
            doesHaveDictionaryDetails.getValue(word).value = result
            if (Log.isLoggable(TAG, Log.DEBUG)) {
                Log.d(TAG, "Thesaurus result for dictionary details for $word is $result")
            }
        }
    }
}
