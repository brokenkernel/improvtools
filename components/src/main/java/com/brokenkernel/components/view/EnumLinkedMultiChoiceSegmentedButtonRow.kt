package com.brokenkernel.components.view

import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier

@Composable
inline fun <reified T : Enum<T>> EnumLinkedMultiChoiceSegmentedButtonRow(
    // TODO: don't pass state up and down. Pass events up and down.
    isSegmentedButtonChecked: SnapshotStateList<Boolean>,
    crossinline enumToName: (T) -> String,
    modifier: Modifier = Modifier,
) {
    MultiChoiceSegmentedButtonRow(modifier = modifier) {
        enumValues<T>().forEach { topic: T ->
            SegmentedButton(
                onCheckedChange = {
                    isSegmentedButtonChecked[topic.ordinal] = !isSegmentedButtonChecked[topic.ordinal]
                },
                checked = isSegmentedButtonChecked[topic.ordinal],
                shape = SegmentedButtonDefaults.itemShape(
                    index = topic.ordinal,
                    count = enumValues<T>().size,
                ),
                // TODO: i18n
                label = { Text(enumToName(topic)) },
            )
        }
    }
}
