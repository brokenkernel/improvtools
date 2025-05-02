package com.brokenkernel.improvtools.timer.data.model

import com.brokenkernel.improvtools.timer.presentation.viewmodel.TimerListViewModel
import kotlinx.coroutines.flow.MutableStateFlow

internal class TimerInfo(
    val title: MutableStateFlow<String>,
    val timerType: TimerListViewModel.TimerType,
    val id: Int,
)
