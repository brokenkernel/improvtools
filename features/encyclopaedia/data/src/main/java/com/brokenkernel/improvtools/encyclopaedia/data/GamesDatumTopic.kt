package com.brokenkernel.improvtools.encyclopaedia.data

import androidx.compose.ui.graphics.vector.ImageVector
import com.brokenkernel.improvtools.encyclopaedia.data.icons.fireplace
import com.brokenkernel.improvtools.encyclopaedia.data.icons.format_quote
import com.brokenkernel.improvtools.encyclopaedia.data.icons.self_improvement
import com.brokenkernel.improvtools.encyclopaedia.data.icons.toys_and_games

// TODO: This should be internal
public enum class GamesDatumTopic(public val icon: ImageVector) {
    GAME(icon = toys_and_games),
    WARMUP(icon = fireplace),
    EXERCISE(icon = format_quote),
    FORMAT(icon = self_improvement),
}
