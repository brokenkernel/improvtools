package com.brokenkernel.components.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.brokenkernel.components.R

@Composable
fun ExpandIcon(
    isExpanded: Boolean,
    modifier: Modifier = Modifier,
) {
    if (isExpanded) {
        Icon(
            Icons.Default.ExpandLess,
            contentDescription = stringResource(R.string.button_collapse),
            modifier = modifier,
        )
    } else {
        Icon(
            Icons.Default.ExpandMore,
            contentDescription = stringResource(R.string.button_expand),
            modifier = modifier,
        )
    }
}

@Preview
@Composable
private fun ExpandIconPreview() {
    MaterialTheme {
        Surface {
            Column {
                ExpandIcon(isExpanded = true)
                ExpandIcon(isExpanded = false)
            }
        }
    }
}
