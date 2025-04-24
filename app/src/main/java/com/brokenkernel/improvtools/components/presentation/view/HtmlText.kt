package com.brokenkernel.improvtools.components.presentation.view

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.Hyphens
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow

// consider pulling this elsewhere?
internal fun openInCustomTab(context: Context, url: Uri) {
    val builder: CustomTabsIntent.Builder = CustomTabsIntent.Builder()
    val customTabsIntent: CustomTabsIntent = builder.build()
    customTabsIntent.launchUrl(context, url)
}

@Composable
internal fun HtmlText(html: String, modifier: Modifier = Modifier, onUrlClick: ((String) -> Unit)) {
    val text: AnnotatedString = AnnotatedString.fromHtml(
        html,
        linkInteractionListener = { linkAnnotation ->
            when (linkAnnotation) {
                is LinkAnnotation.Url -> {
                    val urlString = linkAnnotation.url
                    onUrlClick(urlString)
                }
            }
        },
        linkStyles = TextLinkStyles(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.primary,
                textDecoration = TextDecoration.Underline,
            ),
        ),
    )
    Text(
        text = text,
        modifier = modifier,
        overflow = TextOverflow.Ellipsis,
        style = TextStyle.Default.copy(
            lineBreak = LineBreak.Paragraph,
            hyphens = Hyphens.Auto,
        ),
    )
}
