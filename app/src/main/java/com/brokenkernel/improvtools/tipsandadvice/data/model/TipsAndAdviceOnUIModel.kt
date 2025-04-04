package com.brokenkernel.improvtools.tipsandadvice.data.model

internal data class TipsAndAdviceOnUIModel(val title: String, val content: String) {
    companion object {
        fun getDefault(): TipsAndAdviceOnUIModel {
            return TipsAndAdviceOnUIModel("", "")
        }
    }
}
