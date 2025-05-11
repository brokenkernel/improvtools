package com.brokenkernel.components.view

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
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
import androidx.compose.ui.tooling.preview.Preview
import org.intellij.lang.annotations.Language

@Composable
fun HtmlText(@Language("html") html: String, modifier: Modifier = Modifier) {
    val uriHandler = LocalUriHandler.current

    val text: AnnotatedString = AnnotatedString.fromHtml(
        html,
        linkInteractionListener = { linkAnnotation ->
            when (linkAnnotation) {
                is LinkAnnotation.Url -> {
                    val urlString = linkAnnotation.url
                    uriHandler.openUri(urlString)
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

// @ImprovToolsAllPreviews
@Preview
@Composable
internal fun HtmlTextPreview() {
    MaterialTheme {
        Surface {
            HtmlText(
                """
            |<b>Bold Text</b>
            |<ol>
            |<li>One</li>
            |</ol>
            |<ul>
            |<li>One</li>
            |</ul>
            |<a href="https://improvtools.brokenkernel.com">Link</a>
            |"""
                    .trimMargin(),
            )
        }
    }
}
