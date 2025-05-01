package com.brokenkernel.improvtools.timer.data.repository

import com.brokenkernel.improvtools.timer.data.model.TimerID

// TODO:  doc manager name
// TODO: doc graph layering

class DefaultTimerManager : TimerManager {
    private val currentTimerID: TimerID = TimerID(0)
    override fun getNextID(): TimerID {
        return TimerID(currentTimerID.underlying + 1)
    }
}
