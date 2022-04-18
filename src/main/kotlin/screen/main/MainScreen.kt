package screen.main

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

    val viewModel: MainViewModel by di.instance()
    val scope = rememberCoroutineScope()

    var collapsed by remember { mutableStateOf(false) }
    val rooms = viewModel.rooms.collectAsState()
    val selectedRoom = viewModel.selectedRoom.collectAsState()

    LaunchedEffect(Any()) {
        viewModel.refreshRooms()
    }

    Row(modifier = Modifier.fillMaxHeight()) {
        if (collapsed) {
            CollapsedSideBar(
                collapseIconOnClick = { collapsed = !collapsed },
                rooms = rooms.value,
                selectedRoom = selectedRoom.value,
                selectRoom = { viewModel.selectRoom(it) }
            )
        } else {
            FullSizeSideBar(
                collapseIconOnClick = { collapsed = !collapsed },
                rooms = rooms.value,
                selectedRoom = selectedRoom.value,
                selectRoom = { viewModel.selectRoom(it) },
                addContact = {
                    scope.launch(Dispatchers.IO) {
                        viewModel.addContact(it)
                        viewModel.refreshRooms()
                    }
                }
            )
        }

        ContentScreen(
            selectedRoom = selectedRoom.value,
            theme = theme,
            changeTheme = changeTheme,
        )
    }
}
