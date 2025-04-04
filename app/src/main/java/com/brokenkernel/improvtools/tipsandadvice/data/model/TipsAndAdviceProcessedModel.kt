package com.brokenkernel.improvtools.tipsandadvice.data.model


internal data class TipsAndAdviceProcessedModel(val advice: Map<String, String>) {

    fun asInfiniteSequence(): Sequence<Pair<String, String>> {
        return sequence {
            while (true) {
                yieldAll(advice.entries.map { it ->
                    it.toPair()
                })
            }
        }

    }

    companion object {
        fun getDefault(): TipsAndAdviceProcessedModel {
            return TipsAndAdviceProcessedModel(advice = emptyMap())
        }
    }
}