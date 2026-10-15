package com.brokenkernel.improvtools.coreinfra

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavMetadataKey
import androidx.navigation3.runtime.get
import androidx.navigation3.runtime.metadata
import androidx.navigation3.scene.OverlayScene
import androidx.navigation3.scene.Scene
import androidx.navigation3.scene.SceneStrategy
import androidx.navigation3.scene.SceneStrategyScope

public typealias BottomSheetContent = @Composable (ColumnScope.() -> Unit)

@OptIn(ExperimentalMaterial3Api::class)
internal data class BottomSheetScene<T : Any>(
    override val key: T,
    override val previousEntries: List<NavEntry<T>>,
    override val overlaidEntries: List<NavEntry<T>>,
    private val entry: NavEntry<T>,
    private val modalBottomSheetProperties: ModalBottomSheetProperties,
    private val onBack: () -> Unit,
) : OverlayScene<T> {

    override val entries: List<NavEntry<T>> = listOf(entry)

    lateinit var sheetState: SheetState

    override val content: @Composable (() -> Unit) = {
        sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = false,
        )
        ModalBottomSheet(
            onDismissRequest = onBack,
            properties = modalBottomSheetProperties,
            sheetState = sheetState,
        ) {
            entry.Content()
        }
    }

    override suspend fun onRemove() {
        sheetState.hide()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
public class BottomSheetSceneStrategy<T : Any> : SceneStrategy<T> {

    override fun SceneStrategyScope<T>.calculateScene(entries: List<NavEntry<T>>): Scene<T>? {
        val lastEntry = entries.lastOrNull() ?: return null
        val bottomSheetProperties = lastEntry.metadata[BottomSheetKey] ?: return null
        return bottomSheetProperties.let { properties ->
            @Suppress("UNCHECKED_CAST")
            BottomSheetScene(
                key = lastEntry.contentKey as T,
                previousEntries = entries.dropLast(1),
                overlaidEntries = entries.dropLast(1),
                entry = lastEntry,
                modalBottomSheetProperties = properties,
                onBack = onBack,
            )
        }
    }

    public companion object {
        public fun bottomSheet(
            modalBottomSheetProperties: ModalBottomSheetProperties = ModalBottomSheetProperties(),
        ): Map<String, Any> =
            metadata {
                put(BottomSheetKey, modalBottomSheetProperties)
            }

        public object BottomSheetKey : NavMetadataKey<ModalBottomSheetProperties>
    }
}
