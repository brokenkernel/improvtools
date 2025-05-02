package com.brokenkernel.improvtools.timer.data.repository

// TODO:  doc manager name
// TODO: doc graph layering

class DefaultTimerManager : TimerManager {
    private val currentTimerID: Int = 0
    override fun getNextID(): Int {
        return currentTimerID + 1
    }
}
