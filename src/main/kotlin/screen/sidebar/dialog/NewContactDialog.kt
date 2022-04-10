package screen.sidebar.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberDialogState
import dto.UserInfoDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import model.UserInfo
import org.kodein.di.compose.localDI
import org.kodein.di.instance
import service.ThemeService
import service.UserService
import util.NetworkResult

@Composable
fun NewContactDialog(
    onCloseRequest: (UserInfo?) -> Unit
) {
    val di = localDI()

    val themeService: ThemeService by di.instance()
    val userService: UserService by di.instance()

    val scope = rememberCoroutineScope()
    val theme = themeService.theme

    var query by remember { mutableStateOf("") }
    var userInfos by remember { mutableStateOf(listOf<UserInfo>()) }
    var selectedUser: UserInfo? by remember { mutableStateOf(null) }

    Dialog(
        onCloseRequest = { onCloseRequest(selectedUser) },
        state = rememberDialogState(position = WindowPosition(Alignment.Center)),
        resizable = false,
        title = "New Contact"
    ) {
        Column(
            modifier = Modifier.background(theme.value.background),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row {
                TextField(
                    value = query,
                    onValueChange = {
                        query = it
                        scope.launch(Dispatchers.IO) {
                            when (val response = userService.findUser(query, query)) {
                                is NetworkResult.Success -> {
                                    userInfos = response.value
                                }
                                is NetworkResult.Error -> {}
                            }
                        }
                    }
                )

                Button(onClick = { onCloseRequest(selectedUser) }) {
                    Text("Add")
                }
            }

            LazyColumn {
                items(userInfos.size) { index ->
                    val userInfoDTO = userInfos[index]
                    Row {
                        RadioButton(
                            selected = userInfoDTO == selectedUser,
                            onClick = { selectedUser = userInfoDTO }
                        )

                        Text(userInfoDTO.name)
                    }
                }
            }

        }
    }

}