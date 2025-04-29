package com.brokenkernel.improvtools.encyclopaedia.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brokenkernel.improvtools.TAG
import com.brokenkernel.improvtools.encyclopaedia.api.ThesaurusAPI
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = LoadableSingleWordThesaurusButtonViewModel.Factory::class)
class LoadableSingleWordThesaurusButtonViewModel @AssistedInject constructor(
    @Assisted("word") val word: String,
    val thesaurusAPI: ThesaurusAPI, // todo: this should be accessed through a repository
) : ViewModel() {

    private val _doesHaveDictionaryDetails: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val doesHaveDictionaryDetails: StateFlow<Boolean> = _doesHaveDictionaryDetails

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val result = thesaurusAPI.hasWordDetails(word)
            _doesHaveDictionaryDetails.value = result
            if (Log.isLoggable(TAG, Log.DEBUG)) {
                Log.d(TAG, "Thesaurus result for dictionary details for $word is $result")
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("word") word: String,
        ): LoadableSingleWordThesaurusButtonViewModel
    }
}
