package com.brokenkernel.improvtools.encyclopaedia.presentation.view

import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList

@Composable
internal inline fun <reified T : Enum<T>> EnumLinkedMultiChoiceSegmentedButtonRow(
    isSegementedButtonChecked: SnapshotStateList<Boolean>,  // todo: figure out how to make sure that size matches
    crossinline enumToName: (T) -> String,
) {
    MultiChoiceSegmentedButtonRow {
        enumValues<T>().forEach { topic: T ->
            SegmentedButton(
                onCheckedChange = {
                    isSegementedButtonChecked[topic.ordinal] = !isSegementedButtonChecked[topic.ordinal]
                },
                checked = isSegementedButtonChecked[topic.ordinal],
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