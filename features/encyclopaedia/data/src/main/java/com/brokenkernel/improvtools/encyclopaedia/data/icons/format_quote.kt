package com.brokenkernel.improvtools.encyclopaedia.data.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

@Suppress("CheckReturnValue")
public val format_quote: ImageVector
    get() {
        if (_format_quote != null) {
            return _format_quote!!
        }
        _format_quote =
            ImageVector.Builder(
                name = "format_quote",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 24f,
                viewportHeight = 24f,
            )
                .apply {
                    path(
                        fill = SolidColor(Color.Black),
                        fillAlpha = 1f,
                        stroke = null,
                        strokeAlpha = 1f,
                        strokeLineWidth = 1f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Bevel,
                        strokeLineMiter = 1f,
                        pathFillType = PathFillType.NonZero,
                    ) {
                        moveTo(5.7f, 18f)
                        lineTo(8f, 14f)
                        quadTo(6.35f, 14f, 5.18f, 12.83f)
                        reflectiveQuadTo(4f, 10f)
                        reflectiveQuadTo(5.18f, 7.18f)
                        reflectiveQuadTo(8f, 6f)
                        reflectiveQuadToRelative(2.83f, 1.18f)
                        reflectiveQuadTo(12f, 10f)
                        quadToRelative(0f, 0.57f, -0.14f, 1.06f)
                        reflectiveQuadTo(11.45f, 12f)
                        lineTo(8f, 18f)
                        horizontalLineTo(5.7f)
                        close()
                        moveToRelative(9f, 0f)
                        lineTo(17f, 14f)
                        quadToRelative(-1.65f, 0f, -2.82f, -1.18f)
                        reflectiveQuadTo(13f, 10f)
                        reflectiveQuadTo(14.18f, 7.18f)
                        reflectiveQuadTo(17f, 6f)
                        reflectiveQuadToRelative(2.83f, 1.18f)
                        reflectiveQuadTo(21f, 10f)
                        quadToRelative(0f, 0.57f, -0.14f, 1.06f)
                        reflectiveQuadTo(20.45f, 12f)
                        lineTo(17f, 18f)
                        horizontalLineTo(14.7f)
                        close()
                        moveTo(9.06f, 11.06f)
                        quadTo(9.5f, 10.63f, 9.5f, 10f)
                        reflectiveQuadTo(9.06f, 8.94f)
                        reflectiveQuadTo(8f, 8.5f)
                        reflectiveQuadTo(6.94f, 8.94f)
                        reflectiveQuadTo(6.5f, 10f)
                        reflectiveQuadToRelative(0.44f, 1.06f)
                        reflectiveQuadTo(8f, 11.5f)
                        reflectiveQuadTo(9.06f, 11.06f)
                        close()
                        moveToRelative(9f, 0f)
                        quadTo(18.5f, 10.63f, 18.5f, 10f)
                        reflectiveQuadTo(18.06f, 8.94f)
                        reflectiveQuadTo(17f, 8.5f)
                        reflectiveQuadTo(15.94f, 8.94f)
                        reflectiveQuadTo(15.5f, 10f)
                        reflectiveQuadToRelative(0.44f, 1.06f)
                        reflectiveQuadTo(17f, 11.5f)
                        reflectiveQuadToRelative(1.06f, -0.44f)
                        close()
                        moveTo(17f, 10f)
                        close()
                        moveTo(8f, 10f)
                        close()
                    }
                }
                .build()
        return _format_quote!!
    }

private var _format_quote: ImageVector? = null
