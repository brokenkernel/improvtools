package com.brokenkernel.components.view

import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.InputChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList

// TODO: don't pass state, pass events instead
@OptIn(ExperimentalMaterial3Api::class)
@Composable
inline fun <reified T : Enum<T>> ChipBar(isChipsChecked: SnapshotStateList<Boolean>) {
    // TODO: consider a DropdownMenu instead?
    FlowRow {
        enumValues<T>().forEach { tag ->
            InputChip(
                selected = isChipsChecked[tag.ordinal],
                onClick = {
                    isChipsChecked[tag.ordinal] = !isChipsChecked[tag.ordinal]
                },
                label = { Text(tag.name) }, // TODO: i18n
                enabled = true, // TODO: disable if no items match at all
//                leadingIcon = TODO: from Enum?
//                trailingIcon = TODO: from Enum?
            )
        }
    }
}
