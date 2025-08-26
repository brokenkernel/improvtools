package com.brokenkernel.improvtools.encyclopaedia.data.model

import kotlinx.serialization.Serializable

@Serializable
public data class TipsAndAdviceProcessedModel(val advice: Map<String, String>)
