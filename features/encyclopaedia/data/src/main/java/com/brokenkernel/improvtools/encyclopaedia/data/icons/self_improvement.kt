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
public val self_improvement: ImageVector
    get() {
        if (_self_improvement != null) {
            return _self_improvement!!
        }
        _self_improvement =
            ImageVector.Builder(
                name = "self_improvement",
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
                        moveTo(6.8f, 20f)
                        quadTo(6.05f, 20f, 5.53f, 19.48f)
                        reflectiveQuadTo(5f, 18.2f)
                        quadTo(5f, 17.68f, 5.3f, 17.21f)
                        reflectiveQuadTo(6.1f, 16.55f)
                        lineTo(10f, 15f)
                        verticalLineTo(12.75f)
                        quadTo(8.65f, 14.33f, 6.86f, 15.16f)
                        reflectiveQuadTo(3f, 16f)
                        verticalLineTo(14f)
                        quadToRelative(1.7f, 0f, 3.09f, -0.7f)
                        reflectiveQuadToRelative(2.51f, -2f)
                        lineTo(9.95f, 9.7f)
                        quadToRelative(0.3f, -0.35f, 0.7f, -0.53f)
                        reflectiveQuadTo(11.5f, 9f)
                        horizontalLineToRelative(1f)
                        quadToRelative(0.45f, 0f, 0.85f, 0.17f)
                        reflectiveQuadToRelative(0.7f, 0.53f)
                        lineToRelative(1.35f, 1.6f)
                        quadToRelative(1.13f, 1.3f, 2.51f, 2f)
                        reflectiveQuadTo(21f, 14f)
                        verticalLineToRelative(2f)
                        quadToRelative(-2.07f, 0f, -3.86f, -0.84f)
                        reflectiveQuadTo(14f, 12.75f)
                        verticalLineTo(15f)
                        lineToRelative(3.9f, 1.55f)
                        quadToRelative(0.5f, 0.2f, 0.8f, 0.66f)
                        quadTo(19f, 17.68f, 19f, 18.2f)
                        quadToRelative(0f, 0.75f, -0.52f, 1.28f)
                        reflectiveQuadTo(17.2f, 20f)
                        horizontalLineTo(10f)
                        verticalLineTo(19.5f)
                        quadToRelative(0f, -0.65f, 0.43f, -1.07f)
                        reflectiveQuadTo(11.5f, 18f)
                        horizontalLineToRelative(3f)
                        quadToRelative(0.23f, 0f, 0.36f, -0.14f)
                        reflectiveQuadTo(15f, 17.5f)
                        reflectiveQuadTo(14.86f, 17.14f)
                        reflectiveQuadTo(14.5f, 17f)
                        horizontalLineToRelative(-3f)
                        quadToRelative(-1.05f, 0f, -1.77f, 0.73f)
                        reflectiveQuadTo(9f, 19.5f)
                        verticalLineTo(20f)
                        horizontalLineTo(6.8f)
                        close()
                        moveTo(10.59f, 7.41f)
                        quadTo(10f, 6.82f, 10f, 6f)
                        reflectiveQuadTo(10.59f, 4.59f)
                        reflectiveQuadTo(12f, 4f)
                        reflectiveQuadToRelative(1.41f, 0.59f)
                        quadTo(14f, 5.18f, 14f, 6f)
                        reflectiveQuadTo(13.41f, 7.41f)
                        reflectiveQuadTo(12f, 8f)
                        reflectiveQuadTo(10.59f, 7.41f)
                        close()
                    }
                }
                .build()
        return _self_improvement!!
    }

private var _self_improvement: ImageVector? = null
