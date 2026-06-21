package com.brokenkernel.improvtools.application.presentation.api

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import com.brokenkernel.improvtools.coreinfra.BottomSheetContent

internal val LocalSnackbarHostState: ProvidableCompositionLocal<SnackbarHostState> =
    compositionLocalOf {
        error("No Snackbar Host State")
    }

internal val LocalBottomSheetContentManager: ProvidableCompositionLocal<(BottomSheetContent) -> Unit> =
    compositionLocalOf {
        error("No Bottom Sheet Host State")
    }
