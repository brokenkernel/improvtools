package com.brokenkernel.improvtools.coreinfra

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList

public interface ImprovToolsNavigationKey : Parcelable

@Composable
public fun <T : Parcelable> rememberParcelableBackStack(vararg elements: T): SnapshotStateList<T> =
    rememberSaveable {
        mutableStateListOf(*elements)
    }