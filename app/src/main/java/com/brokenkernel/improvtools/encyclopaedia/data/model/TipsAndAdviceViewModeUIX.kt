package com.brokenkernel.improvtools.encyclopaedia.data.model

import com.brokenkernel.improvtools.datastore.UserSettings
import com.brokenkernel.improvtools.encyclopaedia.android.tipsandadvice.model.TipsAndAdviceViewModeUI
import com.brokenkernel.improvtools.encyclopaedia.android.tipsandadvice.model.TipsAndAdviceViewModeUI.LIST
import com.brokenkernel.improvtools.encyclopaedia.android.tipsandadvice.model.TipsAndAdviceViewModeUI.SWIPEABLE

public fun internalEnumValuebyTipsAndAdviceViewModeUI(taaViewModeUI: TipsAndAdviceViewModeUI): UserSettings.TipsAndTricksViewMode {
    return when (taaViewModeUI) {
        SWIPEABLE -> UserSettings.TipsAndTricksViewMode.VIEW_MODE_SWIPE
        LIST -> UserSettings.TipsAndTricksViewMode.VIEW_MODE_LIST
    }
}

public fun tipsAndAdviceViewModeUIbyInternalEnumValue(taaViewMode: UserSettings.TipsAndTricksViewMode): TipsAndAdviceViewModeUI {
    return when (taaViewMode) {
        UserSettings.TipsAndTricksViewMode.VIEW_MODE_DEFAULT -> LIST
        UserSettings.TipsAndTricksViewMode.VIEW_MODE_LIST -> LIST
        UserSettings.TipsAndTricksViewMode.VIEW_MODE_SWIPE -> SWIPEABLE
        UserSettings.TipsAndTricksViewMode.UNRECOGNIZED -> SWIPEABLE // maybe throw instead?
    }
}
