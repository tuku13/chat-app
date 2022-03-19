package screen.main

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import screen.content.ContentScreen
import screen.sidebar.CollapsedSideBar
import screen.sidebar.FullSizeSideBar
import theme.Theme

@Composable
fun MainScreen(
    theme: Theme,
    changeTheme: () -> Unit
) {
    var collapsed by remember { mutableStateOf(false) }
    val query by remember { mutableStateOf("Group Name") }

    Row(modifier = Modifier.fillMaxHeight()) {
        if (collapsed) {
            CollapsedSideBar(
                theme = theme,
                collapseIconOnClick = { collapsed = !collapsed }
            )
        } else {
            FullSizeSideBar(
                theme = theme,
                collapseIconOnClick = { collapsed = !collapsed }
            )
        }
        ContentScreen(
            query = query,
            theme = theme,
            changeTheme = changeTheme
        )
    }
}
