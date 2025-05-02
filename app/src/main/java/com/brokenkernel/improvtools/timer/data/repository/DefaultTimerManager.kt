package com.brokenkernel.improvtools.timer.data.repository

// TODO:  doc manager name
// TODO: doc graph layering

class DefaultTimerManager : TimerManager {
    private var currentTimerID: Int = 0
    override fun getNextID(): Int {
        currentTimerID += 1
        return currentTimerID
    }
}
