package com.brokenkernel.improvtools.components.presentation.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.components.sidecar.preview.ImprovToolsAllPreviews
import com.brokenkernel.improvtools.ui.theme.ImprovToolsTheme

@Composable
fun ExpandIcon(isExpanded: Boolean) {
    if (isExpanded) {
        Icon(
            Icons.Default.ExpandLess,
            contentDescription = stringResource(R.string.collapse),
        )
    } else {
        Icon(
            Icons.Default.ExpandMore,
            contentDescription = stringResource(R.string.expand),
        )
    }
}

@ImprovToolsAllPreviews
@Composable
fun ExpandIconPreview() {
    ImprovToolsTheme {
        Surface {
            Column {
                ExpandIcon(isExpanded = true)
                ExpandIcon(isExpanded = false)
            }
        }
    }
}
