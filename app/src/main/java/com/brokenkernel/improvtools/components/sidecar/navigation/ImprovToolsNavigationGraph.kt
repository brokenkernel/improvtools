package com.brokenkernel.improvtools.components.sidecar.navigation

import com.brokenkernel.improvtools.application.navigation.LogFirebaseScreenViewWrapper
import com.brokenkernel.improvtools.application.navigation.SetScaffoldStateWrapper
import com.ramcosta.composedestinations.annotation.ExternalDestination
import com.ramcosta.composedestinations.annotation.NavHostGraph
import com.ramcosta.composedestinations.annotation.parameters.CodeGenVisibility
import com.ramcosta.composedestinations.generated.buzzer.destinations.BuzzerTabDestination
import com.ramcosta.composedestinations.generated.encyclopaedia.destinations.EmotionTabDestination
import com.ramcosta.composedestinations.generated.encyclopaedia.destinations.PeopleTabDestination
import com.ramcosta.composedestinations.generated.tonguetwister.destinations.TongueTwisterTabDestination

@MustBeDocumented
@NavHostGraph(
    visibility = CodeGenVisibility.INTERNAL,
)
public annotation class ImprovToolsNavigationGraph {

    @ExternalDestination<TongueTwisterTabDestination>(
        // TODO: make sure matches with ImprovToolsDestination
        wrappers = [
            LogFirebaseScreenViewWrapper::class,
            SetScaffoldStateWrapper::class,
        ],
    )
    @ExternalDestination<BuzzerTabDestination>(
        // TODO: make sure matches with ImprovToolsDestination
        wrappers = [
            LogFirebaseScreenViewWrapper::class,
            SetScaffoldStateWrapper::class,
        ],
    )
    @ExternalDestination<PeopleTabDestination>(
        // TODO: make sure matches with ImprovToolsDestination
        wrappers = [
            LogFirebaseScreenViewWrapper::class,
            SetScaffoldStateWrapper::class,
        ],
    )
    @ExternalDestination<EmotionTabDestination>(
        // TODO: make sure matches with ImprovToolsDestination
        wrappers = [
            LogFirebaseScreenViewWrapper::class,
            SetScaffoldStateWrapper::class,
        ],
    )
    public companion object Includes
}
