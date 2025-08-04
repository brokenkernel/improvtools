package com.brokenkernel.improvtools.tonguetwister.view

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.ExternalModuleGraph
import com.ramcosta.composedestinations.annotation.parameters.CodeGenVisibility

@Destination<ExternalModuleGraph>(
    start = true,
    visibility = CodeGenVisibility.PUBLIC,
)
@Composable
internal fun TongueTwisterTab() {
}
