package com.brokenkernel.improvtools.encyclopaedia.android.api

import androidx.annotation.StringRes
import androidx.navigation3.runtime.NavMetadataKey
import com.brokenkernel.improvtools.coreinfra.ImprovToolsNavigationKey
import kotlinx.parcelize.Parcelize

@Parcelize
public object TipsAndAdviceNavigationKey : ImprovToolsNavigationKey

@Parcelize
public object GamesPageNavigationKey : ImprovToolsNavigationKey

@Parcelize
public object PeoplePageNavigationKey : ImprovToolsNavigationKey

@Parcelize
public object GlossaryPageNavigationKey : ImprovToolsNavigationKey

@Parcelize
public object EmotionsPageNavigationKey : ImprovToolsNavigationKey

@Parcelize
public object ThesaurusAllItemsPageNavigationKey : ImprovToolsNavigationKey

@Parcelize
public data class ThesaurusSingleWordPageNavigationKey(
    val word: String,
    @StringRes val priorTitleResource: Int,
) : ImprovToolsNavigationKey
