package screen.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import screen.content.ContentScreen
import screen.sidebar.CollapsedSideBar
import screen.sidebar.FullSizeSideBar

@Composable
fun MainScreen() {
    var collapsed by remember { mutableStateOf(false) }
    val query by remember { mutableStateOf("") }

    Row(modifier = Modifier.fillMaxHeight()) {
        if (collapsed) {
            CollapsedSideBar(
                collapseIconOnClick = { collapsed = !collapsed }
            )
        } else {
            FullSizeSideBar(
                collapseIconOnClick = { collapsed = !collapsed }
            )
        }
        ContentScreen(query)
    }
}
