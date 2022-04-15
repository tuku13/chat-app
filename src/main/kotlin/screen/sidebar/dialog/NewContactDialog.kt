package screen.sidebar.dialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowSize
import androidx.compose.ui.window.rememberDialogState
import dto.UserInfoDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import model.UserInfo
import org.kodein.di.compose.localDI
import org.kodein.di.instance
import screen.authentication.InputTextField
import screen.sidebar.ColoredButton
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
    val theme = themeService.theme.collectAsState()

    var query by remember { mutableStateOf("") }
    var userInfos by remember { mutableStateOf(listOf<UserInfo>()) }
    var selectedUser: UserInfo? by remember { mutableStateOf(null) }

    Dialog(
        onCloseRequest = { onCloseRequest(selectedUser) },
        state = rememberDialogState(
            position = WindowPosition(Alignment.Center),
            size = DpSize(600.dp, 600.dp)
        ),
        resizable = false,
        title = "New Contact"
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
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
                                    userInfos = response.value
                                }
                                is NetworkResult.Error -> {}
                            }
                        }
                    },
                )

                ColoredButton(
                    text = "Add",
                    color = theme.value.blue,
                    theme = theme.value,
                    onClick = { onCloseRequest(selectedUser) }
                )
            }

            LazyColumn {
                items(userInfos.size) { index ->
                    val userInfoDTO = userInfos[index]
                    val backgroundColor =
                        if (selectedUser == userInfoDTO) Color.Black.copy(alpha = 0.2f) else Color.Transparent
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp)
                            .background(backgroundColor)
                            .clickable {
                                selectedUser = userInfoDTO
                            }
                            .padding(start = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = userInfoDTO.name,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = theme.value.title
                        )
                    }
                }
            }

        }
    }

}