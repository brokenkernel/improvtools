package com.brokenkernel.improvtools.timer.presentation.view

import android.os.Build
import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlarmAdd
import androidx.compose.material.icons.filled.AlarmOff
import androidx.compose.material.icons.filled.AlarmOn
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.TimerOff
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.application.navigation.ImprovToolsDestination
import com.brokenkernel.improvtools.components.presentation.view.OneWayDismissableContent
import com.brokenkernel.improvtools.components.sidecar.navigation.ImprovToolsNavigationGraph
import com.brokenkernel.improvtools.timer.presentation.viewmodel.CountDownTimerState
import com.brokenkernel.improvtools.timer.presentation.viewmodel.CountUpTimerState
import com.brokenkernel.improvtools.timer.presentation.viewmodel.TimerListViewModel
import com.brokenkernel.improvtools.timer.presentation.viewmodel.TimerState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import sh.calvin.reorderable.ReorderableCollectionItemScope
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyListState

private const val TAG = "TimerScreen"

// TODO: allow for optional starting time setting
// TODO control of how many timers. Add some.
// todo: click on title, change name of timer
// TODO: adding timer stops/resets existing timers. See also: state storage is broken.
// TODO: possibly add Timer Edit Button (for future editing time, etc. Also clearer UX than clicking on title to edit title)
// TODO: handle countdown timer when it is done. (a) stop/pause it (b) notification handler

@Composable
private fun CommonTimer(
    timerState: TimerState,
    onRemoveTimer: () -> Unit,
    onTitleChange: (String) -> Unit,
    iconStarted: ImageVector,
    iconPaused: ImageVector,
    iconDescription: String,
    scope: ReorderableCollectionItemScope,
    content: @Composable () -> Unit,
) {
    SlottedTimerCardContent(
        title = timerState.title,
        currentTime = timerState::showTime,
        actions = content,
        isStarted = timerState.isStarted(),
        onRemoveTimer = onRemoveTimer,
        leadingIcon = {
            Icon(
                if (timerState.isStarted()) {
                    iconStarted
                } else {
                    iconPaused
                },
                contentDescription = iconDescription,
            )
        },
        onTitleChange = onTitleChange,
        scope = scope,
    )
}

@Composable
private fun CountDownTimer(
    timerState: TimerState,
    onStartTimer: () -> Unit,
    onPauseTimer: () -> Unit,
    onRemoveTimer: () -> Unit,
    onHalfTimeTimer: () -> Unit,
    onResetTimer: () -> Unit,
    onTitleChange: (String) -> Unit,
    scope: ReorderableCollectionItemScope,
) {
    CommonTimer(
        timerState = timerState,
        content = {
            StartPauseButton(
                timerState,
                onPause = onPauseTimer,
                onStart = onStartTimer,
            )
            OutlinedButton(
                onClick = onHalfTimeTimer,
            ) {
                Text(stringResource(R.string.timer_half_time))
            }
            OutlinedButton(
                onClick = onResetTimer,
            ) {
                Text(stringResource(R.string.timer_reset))
            }
        },
        onRemoveTimer = onRemoveTimer,
        iconPaused = Icons.Default.AlarmOff,
        iconStarted = Icons.Default.AlarmOn,
        iconDescription = stringResource(R.string.count_down_timer),
        onTitleChange = onTitleChange,
        scope = scope,
    )
}

@Composable
private fun CountUpTimer(
    timerState: TimerState,
    onStartTimer: () -> Unit,
    onPauseTimer: () -> Unit,
    onRemoveTimer: () -> Unit,
    onResetTimer: () -> Unit,
    onTitleChange: (String) -> Unit,
    scope: ReorderableCollectionItemScope,
) {
    CommonTimer(
        timerState = timerState,
        content = {
            StartPauseButton(
                timerState,
                onPause = onPauseTimer,
                onStart = onStartTimer,
            )
            OutlinedButton(
                onClick = onResetTimer,
            ) {
                Text(stringResource(R.string.timer_reset))
            }
        },
        iconPaused = Icons.Default.TimerOff,
        iconStarted = Icons.Default.Timer,
        iconDescription = stringResource(R.string.stop_watch),
        onRemoveTimer = onRemoveTimer,
        onTitleChange = onTitleChange,
        scope = scope,
    )
}

@OptIn(ExperimentalPermissionsApi::class)
@ImprovToolsDestination<ImprovToolsNavigationGraph>
@Composable
internal fun TimerTab(viewModel: TimerListViewModel = hiltViewModel()) {
    val haptic = LocalHapticFeedback.current
    val shouldHapticOnRemove = viewModel.shouldHaptic.collectAsStateWithLifecycle()
    val allTimers = viewModel.allTimers
    val lazyListState = rememberLazyListState()
    val notificationPermissionState: MultiplePermissionsState =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            rememberMultiplePermissionsState(
                listOf(
                    android.Manifest.permission.POST_NOTIFICATIONS,
                ),
            )
        } else {
            rememberMultiplePermissionsState(listOf())
        }
    val reorderableLazyListState = rememberReorderableLazyListState(
        lazyListState = lazyListState,
    ) { from, to ->
        viewModel.swapTimer(from.index, to.index)
        haptic.performHapticFeedback(HapticFeedbackType.SegmentFrequentTick)
    }

    if (!notificationPermissionState.allPermissionsGranted) {
        LaunchedEffect(Unit) {
            // TODO
            notificationPermissionState.launchMultiplePermissionRequest()
        }
    }

    LazyColumn(state = lazyListState) {
        items(allTimers, key = { t -> t.timerID }) { timer: TimerState ->
            ReorderableItem(state = reorderableLazyListState, key = timer.timerID) { isDragging ->
                val elevation by animateDpAsState(if (isDragging) 8.dp else 0.dp)
                val currentTimer by rememberUpdatedState(timer)
                val onRemove = {
                    if (shouldHapticOnRemove.value) {
                        haptic.performHapticFeedback(HapticFeedbackType.ToggleOff)
                    }
                    viewModel.removeTimer(currentTimer)
                    Log.w(TAG, "removing timer $timer")
                    Unit
                }

                // TODO: half time doesn't immediately recompose ...
                //  ... since currentTime lambda doesn't change (or nothing triggers recompose)

                OneWayDismissableContent(onRemove = onRemove) {
                    TimerBorderOutlineCard(
                        modifier = Modifier.shadow(elevation),
                    ) {
                        val context = LocalContext.current
                        when (timer) {
                            is CountDownTimerState -> {
                                CountDownTimer(
                                    onPauseTimer = { viewModel.invertTimerState(timer, context) },
                                    onRemoveTimer = onRemove,
                                    onHalfTimeTimer = { viewModel.halfTimer(timer) },
                                    onResetTimer = { viewModel.resetTimer(timer) },
                                    onTitleChange = { viewModel.replaceTitle(timer, it) },
                                    timerState = timer,
                                    onStartTimer = {
                                        viewModel.invertTimerState(timer, context)
                                        if (notificationPermissionState.allPermissionsGranted) {
                                            viewModel.tryToSendNotificationForTimer(timer, context)
                                        } else if (notificationPermissionState.shouldShowRationale) {
                                            // TODO: show dialog.
                                            notificationPermissionState.launchMultiplePermissionRequest()
                                        } else {
                                            notificationPermissionState.launchMultiplePermissionRequest()
                                        }
                                    },
                                    scope = this,
                                )
                            }

                            is CountUpTimerState -> {
                                CountUpTimer(
                                    onPauseTimer = { viewModel.invertTimerState(timer, context) },
                                    onRemoveTimer = onRemove,
                                    onResetTimer = { viewModel.resetTimer(timer) },
                                    onTitleChange = { viewModel.replaceTitle(timer, it) },
                                    timerState = timer,
                                    onStartTimer = {
                                        viewModel.invertTimerState(timer, context)
                                        if (notificationPermissionState.allPermissionsGranted) {
                                            viewModel.tryToSendNotificationForTimer(timer, context)
                                        }
                                    },
                                    scope = this,
                                )
                            }
                        }
                    }
                }
            }
        }
        item(key = "bottom") {
            Row {
                val newStopwatchString = stringResource(R.string.new_stopwatch_timer)
                val newCountdownString = stringResource(R.string.new_countdown_timer)
                LargeFloatingActionButton(
                    onClick = {
                        viewModel.addCountUpTimer(newStopwatchString)
                    },
                ) {
                    Icon(Icons.Filled.Timer, newStopwatchString)
                }
                LargeFloatingActionButton(
                    onClick = {
                        viewModel.addCountDownTimer(newStopwatchString)
                    },
                ) {
                    Icon(Icons.Filled.AlarmAdd, newCountdownString)
                }
            }
        }
    }
}
