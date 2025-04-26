package com.brokenkernel.improvtools.encyclopaedia.data.model

import kotlinx.serialization.Serializable

@Serializable
internal data class TipsAndAdviceProcessedModel(val advice: Map<String, String>)
