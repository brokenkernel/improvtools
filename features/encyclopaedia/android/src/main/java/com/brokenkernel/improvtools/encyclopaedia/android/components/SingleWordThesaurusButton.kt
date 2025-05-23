package com.brokenkernel.improvtools.encyclopaedia.android.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ReadMore
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.brokenkernel.components.view.SimpleIconButton
import com.brokenkernel.improvtools.encyclopaedia.android.R

@Composable
public fun SingleWordThesaurusButton(
    word: String,
    onNavigateToWord: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    SimpleIconButton(
        onClick = {
            onNavigateToWord(word)
        },
        icon = Icons.AutoMirrored.Default.ReadMore,
        contentDescription = stringResource(
            R.string.see_word_details_for,
            word,
        ),
        enabled = enabled,
        modifier = modifier,
    )
}

@Preview
@Composable
private fun ExampleSingleWordThesaurusButtonPreview() {
    MaterialTheme {
        Surface {
            Column {
                Text("enabled")
                SingleWordThesaurusButton(word = "induldge", onNavigateToWord = {})
                Text("disabled")
                SingleWordThesaurusButton(word = "induldge", onNavigateToWord = {}, enabled = false)
            }
        }
    }
}
