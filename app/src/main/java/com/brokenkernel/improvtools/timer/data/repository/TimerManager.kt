package com.brokenkernel.improvtools.timer.data.repository

import com.brokenkernel.improvtools.timer.data.model.TimerID

interface TimerManager {
    fun getNextID(): TimerID
}
