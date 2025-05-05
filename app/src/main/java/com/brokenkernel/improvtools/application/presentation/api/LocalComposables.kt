package com.brokenkernel.improvtools.application.presentation.api

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf

internal typealias BottomSheetContent = @Composable (ColumnScope.() -> Unit)

internal val LocalSnackbarHostState: ProvidableCompositionLocal<SnackbarHostState> =
    compositionLocalOf<SnackbarHostState> {
        error("No Snackbar Host State")
    }

internal val LocalBottomSheetContentManager: ProvidableCompositionLocal<(BottomSheetContent) -> Unit> =
    compositionLocalOf<(BottomSheetContent) -> Unit> {
        error("No Snackbar Host State")
    }
