package com.brokenkernel.improvtools.encyclopaedia.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.brokenkernel.improvtools.encyclopaedia.data.model.GamesDatum

internal class GamesTabViewModel : ViewModel() {
    val sortedGames =
        GamesDatum.sortedBy { it.gameName }.toList()
}
