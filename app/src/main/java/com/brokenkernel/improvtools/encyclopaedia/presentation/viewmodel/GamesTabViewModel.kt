package com.brokenkernel.improvtools.encyclopaedia.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.brokenkernel.improvtools.encyclopaedia.data.model.GamesDataItem
import com.brokenkernel.improvtools.encyclopaedia.data.model.GamesDatum

internal class GamesTabViewModel : ViewModel() {
    val sortedGames: List<GamesDataItem> =
        GamesDatum.sortedBy { it.gameName }.toList()
    val groupedGames: Map<String, List<GamesDataItem>> =
        sortedGames.groupBy { game -> game.gameName[0].toString() }
}
