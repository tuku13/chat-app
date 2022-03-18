package screen.main

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import screen.ConversationScreen
import screen.sidebar.CollapsedSideBar
import screen.sidebar.FullSizeSideBar

@Composable
fun MainScreen() {
    var collapsed by remember { mutableStateOf(false) }

    Row(modifier = Modifier.fillMaxWidth()) {
        if (collapsed) CollapsedSideBar() else FullSizeSideBar()
        ConversationScreen()
    }
}