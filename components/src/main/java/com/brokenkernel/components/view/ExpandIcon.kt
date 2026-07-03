package com.brokenkernel.components.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.brokenkernel.components.R

@Composable
public fun ExpandIcon(
    isExpanded: Boolean,
    modifier: Modifier = Modifier,
) {
    if (isExpanded) {
        Icon(
            painterResource(R.drawable.arrow_drop_up_24dp_1f1f1f_fill0_wght400_grad0_opsz24),
            contentDescription = stringResource(R.string.button_collapse),
            modifier = modifier,
        )
    } else {
        Icon(
            painterResource(R.drawable.arrow_drop_down_24dp_1f1f1f_fill0_wght400_grad0_opsz24),
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
