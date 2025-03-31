package com.brokenkernel.improvtools

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.compose.rememberNavController
import com.brokenkernel.improvtools.application.data.model.NavigableScreens
import com.brokenkernel.improvtools.application.presentation.view.ImprovToolsNavigationDrawer
import com.brokenkernel.improvtools.ui.theme.ImprovToolsTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            OuterContentForMasterScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSuggestionPairList() {
    OuterContentForMasterScreen()
}

@Composable
fun OuterContentForMasterScreen() {
    val drawerNavController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    // the next is should be resolvable by tracking 'current screen metadta' instead of individual state,
    // also annoying since the default is multiple-times replicated, but good enough for now.

    var currentNavigableScreen by rememberSaveable { mutableStateOf(NavigableScreens.SuggestionGenerator) }

    drawerNavController.addOnDestinationChangedListener {
            controller: NavController,
            destination: NavDestination,
            args: Bundle?,
        ->
        val whichScreen = NavigableScreens.byRoute(destination.route)
        if (whichScreen != null) {
            currentNavigableScreen = whichScreen
        }
    }

//    val xyz = resources.openRawResource(R.xml.audience_suggestion_datum)
//    R.raw.audience_suggestion_datum;

//    Resources.getXML()
//    xmlResource()
//    val res: Resources = this.getResources()
//    val xrp = res.getXml(R.xml.your_resId)


    // TODO: use hilt DI
    ImprovToolsTheme {
        Surface {
            ImprovToolsNavigationDrawer(
                drawerState = drawerState,
                drawerNavController = drawerNavController,
                doNavigateToNavigableScreen = { clickedItem: NavigableScreens ->
                    drawerNavController.navigate(clickedItem.route)
                },
                currentNavigableScreen = currentNavigableScreen
            )
        }
    }
}