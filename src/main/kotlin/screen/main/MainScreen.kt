package screen.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import screen.content.ContentScreen
import screen.sidebar.CollapsedSideBar
import screen.sidebar.FullSizeSideBar
import theme.Theme

@Composable
fun MainScreen() {
    var collapsed by remember { mutableStateOf(false) }
    val query by remember { mutableStateOf("Group Name") }

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
