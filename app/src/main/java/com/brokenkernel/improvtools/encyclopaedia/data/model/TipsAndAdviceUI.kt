package com.brokenkernel.improvtools.encyclopaedia.data.model

import com.brokenkernel.improvtools.datastore.UserSettings.TipsAndTricksViewMode

internal data class TipContentUI(
    val title: String,
    val content: String,
)

enum class TipsAndAdviceViewModeUI(
    val internalEnumMatching: TipsAndTricksViewMode,
) {
    SWIPEABLE(
        internalEnumMatching =
        TipsAndTricksViewMode.VIEW_MODE_SWIPE,
    ),
    LIST(
        internalEnumMatching =
        TipsAndTricksViewMode.VIEW_MODE_LIST,
    ),
    ;

    companion object {
        fun byInternalEnumValue(v: TipsAndTricksViewMode): TipsAndAdviceViewModeUI {
            return when (v) {
                TipsAndTricksViewMode.VIEW_MODE_DEFAULT -> LIST
                TipsAndTricksViewMode.VIEW_MODE_LIST -> LIST
                TipsAndTricksViewMode.VIEW_MODE_SWIPE -> SWIPEABLE
                TipsAndTricksViewMode.UNRECOGNIZED -> SWIPEABLE // maybe throw instead?
            }
        }
    }
}

internal data class TipsAndAdviceUI(
    val tipsAndAdvice: List<TipContentUI>,
)
