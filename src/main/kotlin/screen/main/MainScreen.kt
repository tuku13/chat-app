package screen.main

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import org.kodein.di.compose.localDI
import org.kodein.di.instance
import screen.content.ContentScreen
import screen.sidebar.CollapsedSideBar
import screen.sidebar.FullSizeSideBar
import theme.Theme

@Composable
fun MainScreen(
    theme: Theme,
    changeTheme: () -> Unit
) {
    val di = localDI()

    var collapsed by remember { mutableStateOf(false) }
    val query by remember { mutableStateOf("Group Name") }
    val viewModel: MainViewModel by di.instance()

    val rooms = viewModel.rooms.collectAsState().value
    val selectedRoom = viewModel.selectedRoom.collectAsState().value

    LaunchedEffect(Any()) {
        viewModel.getRooms()
    }

    Row(modifier = Modifier.fillMaxHeight()) {
        if (collapsed) {
            CollapsedSideBar(
                collapseIconOnClick = { collapsed = !collapsed },
                rooms = rooms,
                selectedRoom = selectedRoom,
                selectRoom = { viewModel.selectRoom(it) }
            )
        } else {
            FullSizeSideBar(
                collapseIconOnClick = { collapsed = !collapsed },
                rooms = rooms,
                selectedRoom = selectedRoom,
                selectRoom = { viewModel.selectRoom(it) }
            )
        }
        ContentScreen(
            query = query,
            theme = theme,
            changeTheme = changeTheme,
        )
    }
}
