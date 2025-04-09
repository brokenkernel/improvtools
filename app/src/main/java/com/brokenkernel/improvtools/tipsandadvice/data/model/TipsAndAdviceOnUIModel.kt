package com.brokenkernel.improvtools.tipsandadvice.data.model

import androidx.annotation.Size
import com.brokenkernel.improvtools.datastore.UserSettings.TipsAndTricksViewMode

internal data class TipContentUIModel(
    val title: String,
    val content: String,
)

enum class TipsAndAdviceViewModeUI(@Size(min = 1) val internalEnumsMatching: List<TipsAndTricksViewMode>) {
    SWIPEABLE(
        internalEnumsMatching = listOf(
            TipsAndTricksViewMode.VIEW_MODE_SWIPE,
        )
    ),
    LIST(
        internalEnumsMatching = listOf(
            TipsAndTricksViewMode.VIEW_MODE_LIST,
            TipsAndTricksViewMode.VIEW_MODE_DEFAULT,
        )
    ),
    ;

    companion object {
        fun byInternalEnumValue(v: TipsAndTricksViewMode): TipsAndAdviceViewModeUI {
            return when (v) {
                TipsAndTricksViewMode.VIEW_MODE_DEFAULT -> SWIPEABLE
                TipsAndTricksViewMode.VIEW_MODE_LIST -> LIST
                TipsAndTricksViewMode.VIEW_MODE_SWIPE -> SWIPEABLE
                TipsAndTricksViewMode.UNRECOGNIZED -> SWIPEABLE // maybe throw instead?
            }
        }
    }
}

internal data class TipsAndAdviceOnUIModel(
    val tipsAndAdvice: List<TipContentUIModel>,
)
