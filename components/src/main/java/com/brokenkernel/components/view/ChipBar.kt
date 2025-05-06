package com.brokenkernel.components.view

import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.util.fastAny
import com.brokenkernel.components.R
import kotlinx.collections.immutable.ImmutableList

// TODO: don't pass state, pass events instead
@OptIn(ExperimentalMaterial3Api::class)
@Composable
inline fun <reified T : Enum<T>> ChipBar(
    isChipsChecked: ImmutableList<Boolean>,
    crossinline onChipClick: (Int) -> Unit,
    noinline onClearClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    // TODO: consider a DropdownMenu instead?
    FlowRow(modifier = modifier) {
        enumValues<T>().forEach { tag ->
            FilterChip(
                selected = isChipsChecked[tag.ordinal],
                onClick = {
                    onChipClick(tag.ordinal)
                },
                label = { Text(tag.name) }, // TODO: i18n
                enabled = true, // TODO: disable if no items match at all
//                leadingIcon = TODO: from Enum?
//                trailingIcon = TODO: from Enum?
            )
        }
//
        // TODO: add tooltipbox wrapper. This currently causes compiler bug
        IconButton(
            onClick = onClearClick,
            enabled = isChipsChecked.fastAny { it },
        ) {
            Icon(
                Icons.Default.Clear,
                contentDescription = stringResource(R.string.clear_all),
            )
        }
    }
}
