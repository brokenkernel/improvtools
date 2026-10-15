package com.brokenkernel.improvtools.coreinfra

import android.os.Bundle
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation3.runtime.EntryProviderScope
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.onEach

public typealias EntryProviderInstaller = EntryProviderScope<ImprovToolsNavigationKey>.() -> Unit

@ActivityRetainedScoped
public class ImprovToolsNavigator internal constructor(
    backStack: BackStack,
) {
    // Needs to be public because NavDisplay directly observes it.
    public val backstack: BackStack = backStack

    public fun goTo(destination: ImprovToolsNavigationKey) {
        backstack.add(destination)
    }

    public fun goBack() {
        backstack.removeLastOrNull()
    }

    public fun current(): ImprovToolsNavigationKey {
        return backstack.last()
    }

    public object Factory {
        @Composable
        public fun create(initialRoute: ImprovToolsNavigationKey): ImprovToolsNavigator {
            val backstack: SnapshotStateList<ImprovToolsNavigationKey> =
                rememberParcelableBackStack<ImprovToolsNavigationKey>(
                    initialRoute,
                )

            LaunchedEffect(backstack.last()) {
                val entry: ImprovToolsNavigationKey = backstack.last()
                val params = Bundle()
                params.putString(
                    FirebaseAnalytics.Param.SCREEN_NAME,
                    entry.toString(),
                )
                params.putString(FirebaseAnalytics.Param.SCREEN_CLASS, entry.toString())
                Firebase.analytics.logEvent(
                    FirebaseAnalytics.Event.SCREEN_VIEW,
                    params,
                )
            }

            LaunchedEffect(backstack.toList()) {
                if (Log.isLoggable(TAG, Log.DEBUG)) {
                    Log.d(TAG, """Back stack: ${backstack.joinToString(" -> ")}""")
                }
            }

            return ImprovToolsNavigator(backstack)
        }
    }
}
