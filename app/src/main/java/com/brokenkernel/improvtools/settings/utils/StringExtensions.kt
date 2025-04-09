package com.brokenkernel.improvtools.settings.utils

import androidx.annotation.RestrictTo

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
internal fun String.toTitleCase(splitDelimiter: String = " ", joinDelimiter: String = " "): String {
    return split(splitDelimiter).joinToString(joinDelimiter) { word ->
        val lowercaseWord = word.lowercase()
        lowercaseWord.replaceFirstChar(Char::titlecaseChar)
    }
}
