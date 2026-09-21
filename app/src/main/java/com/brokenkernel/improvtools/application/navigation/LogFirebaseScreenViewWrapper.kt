package com.brokenkernel.improvtools.application.navigation

/*
internal object LogFirebaseScreenViewWrapper : DestinationWrapper {

    @SuppressWarnings("ComposableLambdaParameterNaming")
    @Composable
    override fun <T> DestinationScope<T>.Wrap(screenContent: @Composable (() -> Unit)) {
        val navigableScreen: NavigableScreens = NavigableScreens.byRoute(destination)
        val resources = LocalResources.current

        val params = Bundle()
        params.putString(
            FirebaseAnalytics.Param.SCREEN_NAME,
            resources.getString(navigableScreen.titleResource),
        )
        params.putString(FirebaseAnalytics.Param.SCREEN_CLASS, destination.label?.toString())
        LaunchWrapper(
            onLaunchCallback = {
                Firebase.analytics.logEvent(
                    FirebaseAnalytics.Event.SCREEN_VIEW,
                    params,
                )
            },
        ) {
            screenContent()
        }
    }
}
*/
