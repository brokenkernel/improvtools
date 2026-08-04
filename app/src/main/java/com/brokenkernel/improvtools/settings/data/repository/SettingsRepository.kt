package com.brokenkernel.improvtools.settings.data.repository

import com.brokenkernel.improvtools.datastore.UserSettings
import kotlinx.coroutines.flow.Flow

public interface SettingsRepository {
    public val userSettingsFlow: Flow<UserSettings>

    public suspend fun updateAllowAnalyticsCookieStorage(allowAnalyticsCookieStorage: Boolean)

    public suspend fun updateTimerHapticsMode(timerHapticsMode: UserSettings.TimerHapticsMode)
}
