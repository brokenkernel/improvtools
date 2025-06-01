package com.brokenkernel.improvtools.encyclopaedia.data.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Fireplace
import androidx.compose.material.icons.outlined.FormatQuote
import androidx.compose.material.icons.outlined.Games
import androidx.compose.material.icons.outlined.SelfImprovement
import androidx.compose.ui.graphics.vector.ImageVector

internal enum class GamesDatumTopic(val icon: ImageVector) {
    GAME(icon = Icons.Outlined.Games),
    WARMUP(icon = Icons.Outlined.Fireplace),
    EXERCISE(icon = Icons.Outlined.FormatQuote),
    FORMAT(icon = Icons.Outlined.SelfImprovement),
}
