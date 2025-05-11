package com.brokenkernel.improvtools.application.navigation

import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.parameters.CodeGenVisibility
import com.ramcosta.composedestinations.annotation.parameters.DeepLink
import com.ramcosta.composedestinations.spec.DestinationStyle
import com.ramcosta.composedestinations.wrapper.DestinationWrapper
import kotlin.reflect.KClass

@MustBeDocumented
@Repeatable
@Destination<Nothing>(
    visibility = CodeGenVisibility.INTERNAL,
    wrappers = [
        LogFirebaseScreenViewWrapper::class,
        SetScaffoldStateWrapper::class,
    ],
)
internal annotation class ImprovToolsDestination<T : Annotation>(
    val route: String = Destination.COMPOSABLE_NAME,
    val start: Boolean = false,
    val navArgs: KClass<*> = Nothing::class,
    val deepLinks: Array<DeepLink> = [],
    val style: KClass<out DestinationStyle> = DestinationStyle.Default::class,
    val wrappers: Array<KClass<out DestinationWrapper>> = [],
)
