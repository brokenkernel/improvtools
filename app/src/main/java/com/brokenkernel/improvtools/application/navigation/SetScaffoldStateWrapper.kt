package com.brokenkernel.improvtools.application.navigation

// internal object SetScaffoldStateWrapper : DestinationWrapper {
//    @SuppressWarnings("ComposableLambdaParameterNaming")
//    @Composable
//    override fun <T> DestinationScope<T>.Wrap(screenContent: @Composable (() -> Unit)) {
//        val navigableScreen: NavigableScreens = NavigableScreens.byRoute(destination)
//        val depContainer: DestinationDependenciesContainer = buildDependencies()
//        val improvToolsAppState: ImprovToolsAppState = depContainer.require()
//
//        LaunchWrapper(
//            onLaunchCallback = {
//                improvToolsAppState.setScaffoldData(
//                    navigableScreen.titleResource,
//                    navigableScreen.extraMenu,
//                )
//            },
//        ) {
//            screenContent()
//        }
//    }
// }
