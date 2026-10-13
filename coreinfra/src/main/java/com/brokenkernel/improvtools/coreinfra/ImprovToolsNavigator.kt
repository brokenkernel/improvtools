package com.brokenkernel.improvtools.coreinfra

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation3.runtime.EntryProviderScope
import dagger.hilt.android.scopes.ActivityRetainedScoped

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

            LaunchedEffect(backstack.toList()) {
                if (Log.isLoggable(TAG, Log.DEBUG)) {
                    Log.d(TAG, """Back stack: ${backstack.joinToString(" -> ")}""")
                }
            }

            return ImprovToolsNavigator(backstack)
        }
    }
}
