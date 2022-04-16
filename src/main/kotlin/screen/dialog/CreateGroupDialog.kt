package screen.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberDialogState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import model.User
import org.kodein.di.compose.localDI
import org.kodein.di.instance
import screen.authentication.InputTextField
import screen.sidebar.ColoredButton
import service.AuthenticationService
import service.ThemeService
import service.UserService
import util.NetworkResult

@Composable
fun CreateGroupDialog(
    onCloseRequest: (String, List<User>) -> Unit
) {
    val di = localDI()

    val themeService: ThemeService by di.instance()
    val userService: UserService by di.instance()
    val authenticationService: AuthenticationService by di.instance()

    val scope = rememberCoroutineScope()
    val theme = themeService.theme.collectAsState()


    var query by remember { mutableStateOf("") }
    var groupName by remember { mutableStateOf("") }
    var addedUsers by remember { mutableStateOf<List<User>>(emptyList()) }

    var users by remember { mutableStateOf(listOf<User>()) }


    Dialog(
        onCloseRequest = { onCloseRequest("", emptyList()) },
        state = rememberDialogState(
            position = WindowPosition(Alignment.Center),
            size = DpSize(1024.dp, 600.dp)
        ),
        resizable = false,
        title = "Create Group"
    ) {
        Row {
            // left column
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1.0f)
                    .background(theme.value.background)
                    .padding(8.dp),
            ) {
                Row(
                    modifier = Modifier
                        .background(Color.Transparent)
                        .fillMaxWidth()
                        .height(80.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Find user:",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = theme.value.title
                    )

                    InputTextField(
                        value = query,
                        onValueChange = {
                            query = it
                            scope.launch(Dispatchers.IO) {
                                when (val response = userService.findUser(query, query)) {
                                    is NetworkResult.Success -> {
                                        users = response.value
                                    }
                                    is NetworkResult.Error -> {}
                                }
                            }
                        },
                    )

                }

                LazyColumn {
                    items(users.size) { index ->
                        val userInfoDTO = users[index]

                        if (!addedUsers.contains(userInfoDTO) && userInfoDTO.id != authenticationService.userId) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(64.dp)
                                    .clickable { }
                                    .padding(horizontal = 16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = userInfoDTO.name,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = theme.value.title
                                )

                                ColoredButton(
                                    text = "Add",
                                    color = theme.value.green,
                                    theme = theme.value,
                                    onClick = { addedUsers = addedUsers + userInfoDTO }
                                )
                            }
                        }
                    }
                }

            }

            // right column
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(600.dp)
                    .background(theme.value.background)
                    .padding(8.dp),
            ) {
                Row(
                    modifier = Modifier
                        .background(Color.Transparent)
                        .fillMaxWidth()
                        .height(80.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Group name:",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = theme.value.title
                    )

                    InputTextField(
                        value = groupName,
                        onValueChange = {
                            groupName = it
                        },
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    ColoredButton(
                        text = "Create Group",
                        color = theme.value.blue,
                        theme = theme.value,
                        onClick = {
                            if (groupName.isNotBlank() && addedUsers.isNotEmpty()) {
                                onCloseRequest(groupName, addedUsers)
                            }
                            onCloseRequest("", emptyList())
                        }
                    )
                }

                LazyColumn {
                    item {
                        Text(
                            text = "Added users:",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = theme.value.title
                        )
                    }

                    items(addedUsers) { userInfoDTO ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(64.dp)
                                .clickable { }
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = userInfoDTO.name,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = theme.value.title
                            )

                            ColoredButton(
                                text = "Remove",
                                color = theme.value.red,
                                theme = theme.value,
                                icon = null,
                                onClick = { addedUsers = addedUsers - userInfoDTO },
                            )
                        }
                    }
                }
            }

        }

    }
}