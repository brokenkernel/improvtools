package com.brokenkernel.components.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import kotlin.enums.enumEntries

@JvmSynthetic // hides this from Java code, thus making it private again
@PublishedApi
internal fun String.toTitleCase(splitDelimiter: String = " ", joinDelimiter: String = " "): String {
    return split(splitDelimiter).joinToString(joinDelimiter) { word ->
        val lowercaseWord = word.lowercase()
        lowercaseWord.replaceFirstChar(Char::titlecaseChar)
    }
}

@Composable
inline fun <reified T : Enum<T>, reified V : Enum<V>> EnumerationRadioSelection(
    crossinline onEnumerationSelection: (T) -> Unit,
    currentlySelected: V,
    uiToInternalMapping: (T) -> V,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.selectableGroup()) {
        enumEntries<T>().forEach { opt: T ->
            Row(
                Modifier
                    .selectable(
                        selected = uiToInternalMapping(opt) == currentlySelected,
                        onClick = { onEnumerationSelection(opt) },
                        role = Role.RadioButton,
                    ),
            ) {
                RadioButton(
                    selected = uiToInternalMapping(opt) == currentlySelected,
                    onClick = null,
                )
                Text(
                    // TODO: consider making toStringRes or something
                    // or other for i18n and not make translation in component
                    text = opt.name.toTitleCase("_", " "),
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
    }
}
