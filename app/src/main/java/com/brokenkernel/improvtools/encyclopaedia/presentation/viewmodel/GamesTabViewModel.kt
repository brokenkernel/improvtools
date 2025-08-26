package com.brokenkernel.improvtools.encyclopaedia.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.brokenkernel.improvtools.encyclopaedia.data.model.GamesDataItem
import com.brokenkernel.improvtools.encyclopaedia.data.model.GamesDatum
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableMap

internal class GamesTabViewModel : ViewModel() {
    private val sortedGames: ImmutableList<GamesDataItem> =
        GamesDatum.sortedBy { it.gameName }.toImmutableList()
    val groupedGames: ImmutableMap<String, List<GamesDataItem>> =
        sortedGames.groupBy { game -> game.gameName[0].uppercase() }.toImmutableMap()
}
