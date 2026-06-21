package com.brokenkernel.improvtools.encyclopaedia.data.model

import kotlinx.serialization.Serializable

@Serializable
public class TipsAndAdviceProcessedModel(public val advice: Map<String, String>)
