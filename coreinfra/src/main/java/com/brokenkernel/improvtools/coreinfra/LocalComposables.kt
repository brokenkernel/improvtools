package com.brokenkernel.improvtools.coreinfra

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf

public val LocalSnackbarHostState: ProvidableCompositionLocal<SnackbarHostState> =
    compositionLocalOf {
        error("No Snackbar Host State")
    }

public val LocalBottomSheetContentManager: ProvidableCompositionLocal<(BottomSheetContent) -> Unit> =
    compositionLocalOf {
        error("No Bottom Sheet Host State")
    }
