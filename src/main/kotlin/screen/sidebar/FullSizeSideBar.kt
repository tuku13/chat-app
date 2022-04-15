package screen.sidebar

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import model.Room
import model.UserInfo
import org.kodein.di.compose.localDI
import org.kodein.di.instance
import screen.main.MainViewModel
import screen.sidebar.dialog.CreateGroupDialog
import screen.sidebar.dialog.JoinGroupDialog
import screen.sidebar.dialog.NewContactDialog
import service.ThemeService

@Composable
@Preview
fun FullSizeSideBar(
    collapseIconOnClick: () -> Unit,
    rooms: List<Room>,
    selectedRoom: Room?,
    selectRoom: (Room) -> Unit,
    addContact: (UserInfo) -> Unit
) {
    val di = localDI()

    val themeService: ThemeService by di.instance()

    val theme = themeService.theme.value
    val scope = rememberCoroutineScope()
    var query by remember { mutableStateOf("") }
    var isCreateGroupDialogOpen by remember { mutableStateOf(false) }
    var isJoinGroupDialogOpen by remember { mutableStateOf(false) }
    var isNewContactDialogOpen by remember { mutableStateOf(false) }

    val viewModel: MainViewModel by di.instance()

    Column(
        modifier = Modifier.width(328.dp)
            .fillMaxHeight()
            .background(theme.background)
            .border(BorderStroke(1.dp, color = theme.border))
    ) {

        SearchBar(
            onValueChange = { query = it },
            collapseIconOnClick = collapseIconOnClick,
            theme = theme
        )

        Box(modifier = Modifier.weight(1.0f)) {
            ContactScreen(
                query = query,
                rooms = rooms,
                selectedRoom = selectedRoom,
                selectRoom = selectRoom
            )
        }

        Box(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(BorderStroke(1.dp, color = theme.border)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(modifier = Modifier.height(16.dp))

                ColoredButton(
                    text = "Create Group",
                    color = theme.green,
                    theme = theme,
                    onClick = { isCreateGroupDialogOpen = true }
                )

                Box(modifier = Modifier.height(16.dp))

                ColoredButton(
                    text = "Join Group",
                    color = theme.green,
                    theme = theme,
                    onClick = { isJoinGroupDialogOpen = true }
                )

                Box(modifier = Modifier.height(16.dp))

                ColoredButton(
                    text = "New Contact",
                    color = theme.blue,
                    theme = theme,
                    onClick = { isNewContactDialogOpen = true }
                )

                Box(modifier = Modifier.height(16.dp))

                if(isNewContactDialogOpen) {
                    NewContactDialog {
                        isNewContactDialogOpen = false
                        it?.let { addContact(it) }
                    }
                }

                if(isCreateGroupDialogOpen) {
                    CreateGroupDialog { groupName, users ->
                        isCreateGroupDialogOpen = false
                        scope.launch {
                            viewModel.createRoom(groupName, users)
                        }
                    }
                }

                if(isJoinGroupDialogOpen) {
                    JoinGroupDialog { roomId ->
                        isJoinGroupDialogOpen = false
                        scope.launch {
                            viewModel.joinRoom(roomId)
                        }
                    }
                }
            }
        }
    }
}
