package com.brokenkernel.improvtools.encyclopaedia.android.api

import com.brokenkernel.improvtools.coreinfra.ImprovToolsNavigationKey
import com.brokenkernel.improvtools.encyclopaedia.data.GamesDatumTag
import kotlinx.parcelize.Parcelize

@Parcelize
public object TipsAndAdviceNavigationKey : ImprovToolsNavigationKey

@Parcelize
public object GamesPageNavigationKey : ImprovToolsNavigationKey

@Parcelize
public data class SingleTagBottomTabNavigationKey(val tag: GamesDatumTag) : ImprovToolsNavigationKey

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
    val priorTitle: String,
) : ImprovToolsNavigationKey
