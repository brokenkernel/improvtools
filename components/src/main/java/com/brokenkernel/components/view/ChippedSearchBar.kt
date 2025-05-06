package com.brokenkernel.components.view

import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Icecream
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.constraintlayout.solver.widgets.Optimizer.enabled

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
                leadingIcon = {
                    // TODO
                    Icon(
                        Icons.Default.Icecream,
                        contentDescription = "hallo",
                    )
                },
                trailingIcon = {
                    // TODO
                    Icon(
                        Icons.Default.People,
                        contentDescription = "hallo",
                    )
                },
            )
        }
    }
}
