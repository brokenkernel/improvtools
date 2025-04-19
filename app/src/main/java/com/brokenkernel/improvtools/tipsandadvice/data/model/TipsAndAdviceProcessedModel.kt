package com.brokenkernel.improvtools.tipsandadvice.data.model

import kotlinx.serialization.Serializable

@Serializable
internal data class TipsAndAdviceProcessedModel(val advice: Map<String, String>)
