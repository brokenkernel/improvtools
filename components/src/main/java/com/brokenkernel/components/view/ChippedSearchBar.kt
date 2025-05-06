package com.brokenkernel.components.view

import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.InputChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.collections.immutable.ImmutableList

// TODO: don't pass state, pass events instead
@OptIn(ExperimentalMaterial3Api::class)
@Composable
inline fun <reified T : Enum<T>> ChipBar(
    isChipsChecked: ImmutableList<Boolean>,
    crossinline onChipClicked: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    // TODO: consider a DropdownMenu instead?
    FlowRow(modifier = modifier) {
        enumValues<T>().forEach { tag ->
            InputChip(
                selected = isChipsChecked[tag.ordinal],
                onClick = {
                    onChipClicked(tag.ordinal)
                },
                label = { Text(tag.name) }, // TODO: i18n
                enabled = true, // TODO: disable if no items match at all
//                leadingIcon = TODO: from Enum?
//                trailingIcon = TODO: from Enum?
            )
        }
    }
}
