package com.brokenkernel.improvtools.encyclopaedia.data.model

import com.brokenkernel.improvtools.datastore.UserSettings

enum class TipsAndAdviceViewModeUI(
    val internalEnumMatching: UserSettings.TipsAndTricksViewMode,
) {
    SWIPEABLE(
        internalEnumMatching =
        UserSettings.TipsAndTricksViewMode.VIEW_MODE_SWIPE,
    ),
    LIST(
        internalEnumMatching =
        UserSettings.TipsAndTricksViewMode.VIEW_MODE_LIST,
    ),
    ;

    companion object {
        fun byInternalEnumValue(v: UserSettings.TipsAndTricksViewMode): TipsAndAdviceViewModeUI {
            return when (v) {
                UserSettings.TipsAndTricksViewMode.VIEW_MODE_DEFAULT -> LIST
                UserSettings.TipsAndTricksViewMode.VIEW_MODE_LIST -> LIST
                UserSettings.TipsAndTricksViewMode.VIEW_MODE_SWIPE -> SWIPEABLE
                UserSettings.TipsAndTricksViewMode.UNRECOGNIZED -> SWIPEABLE // maybe throw instead?
            }
        }
    }
}
