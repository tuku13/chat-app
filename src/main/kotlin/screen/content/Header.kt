package screen.content

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dehaze
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import model.Room
import org.kodein.di.compose.localDI
import org.kodein.di.instance
import repository.RoomRepository
import screen.main.MainViewModel
import service.AuthenticationService
import service.ThemeService
import theme.Theme

@Composable
fun Header(
    selectedRoom: Room?,
    changeTheme: () -> Unit,
) {
    val di = localDI()

    val authenticationService: AuthenticationService by di.instance()
    val themeService: ThemeService by di.instance()

    val viewModel: MainViewModel by di.instance()
    val theme = themeService.theme
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(79.dp)
            .border(BorderStroke(1.dp, color = theme.value.border))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(modifier = Modifier.size(40.dp))

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = selectedRoom?.name ?: "",
                    fontSize = 18.sp,
                    color = theme.value.chatText
                )

                if (selectedRoom != null) {
                    Spacer(modifier = Modifier.height(8.dp))

                    CompositionLocalProvider(
                        LocalTextSelectionColors provides TextSelectionColors(
                            handleColor = Color.Black.copy(alpha = 0.2f),
                            backgroundColor = Color.Black.copy(alpha = 0.2f)
                        )
                    ) {
                        Row {
                            Text(
                                text = "Join code: ",
                                fontSize = 14.sp,
                                color = theme.value.body
                            )
                            SelectionContainer {
                                Text(
                                    text = selectedRoom.id,
                                    fontSize = 14.sp,
                                    color = theme.value.body,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                        }
                    }
                }
            }


            var expanded by remember { mutableStateOf(false) }

            Box {
                Icon(
                    tint = theme.value.blue,
                    imageVector = Icons.Default.Dehaze,
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clickable { expanded = true }
                )

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    DropdownMenuItem(onClick = {
                        expanded = false
                        changeTheme()
                    }) {
                        Text(text = "Change Theme")
                    }

                    DropdownMenuItem(onClick = {
                        expanded = false

                        selectedRoom?.let {
                            scope.launch(Dispatchers.IO) {
                                viewModel.leaveGroup(selectedRoom.id)
                            }
                        }

                    }) {
                        Text(
                            text = "Leave Group",
                            color = Color.Red
                        )
                    }

                    DropdownMenuItem(onClick = {
                        expanded = false

                        scope.launch(Dispatchers.IO) {
                            authenticationService.logout()
                        }
                    }) {
                        Text(
                            text = "Logout",
                            color = Color.Red
                        )
                    }
                }
            }
        }
    }
}