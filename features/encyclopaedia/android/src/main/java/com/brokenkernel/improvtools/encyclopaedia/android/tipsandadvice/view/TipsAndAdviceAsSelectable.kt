import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.brokenkernel.improvtools.encyclopaedia.android.tipsandadvice.model.TipsAndAdviceViewModeUI
import com.brokenkernel.improvtools.encyclopaedia.android.tipsandadvice.view.TipsAndAdviceScreenAsList
import com.brokenkernel.improvtools.encyclopaedia.android.tipsandadvice.view.TipsAndAdviceScreenAsSwipable
import com.brokenkernel.improvtools.encyclopaedia.data.tipsandadvice.TipsAndAdviceUIState

@Composable
public fun TipsAndAdviceAsSelectable(
    taaViewMode: TipsAndAdviceViewModeUI,
    uiState: TipsAndAdviceUIState,
    modifier: Modifier = Modifier,
) {
    when (taaViewMode) {
        TipsAndAdviceViewModeUI.SWIPEABLE -> TipsAndAdviceScreenAsSwipable(uiState, modifier = modifier)
        TipsAndAdviceViewModeUI.LIST -> TipsAndAdviceScreenAsList(uiState, modifier = modifier)
    }
}
