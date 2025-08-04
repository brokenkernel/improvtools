package com.brokenkernel.improvtools.components.sidecar.navigation

import com.brokenkernel.improvtools.application.navigation.LogFirebaseScreenViewWrapper
import com.brokenkernel.improvtools.application.navigation.SetScaffoldStateWrapper
import com.ramcosta.composedestinations.annotation.ExternalDestination
import com.ramcosta.composedestinations.annotation.NavHostGraph
import com.ramcosta.composedestinations.annotation.parameters.CodeGenVisibility
import com.ramcosta.composedestinations.generated.destinations.TongueTwisterTabDestination

@MustBeDocumented
@NavHostGraph(
    visibility = CodeGenVisibility.INTERNAL,
)
annotation class ImprovToolsNavigationGraph {
    @ExternalDestination<TongueTwisterTabDestination>(
        // TODO: make sure matches with ImprovToolsDestination
        wrappers = [
            LogFirebaseScreenViewWrapper::class,
            SetScaffoldStateWrapper::class,
        ],
    )
    companion object Includes
}
