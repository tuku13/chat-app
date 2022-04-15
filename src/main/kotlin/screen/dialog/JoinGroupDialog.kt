package screen.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberDialogState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.compose.localDI
import org.kodein.di.instance
import screen.authentication.InputTextField
import screen.sidebar.ColoredButton
import service.ThemeService
import util.NetworkResult

@Composable
fun JoinGroupDialog(
    onCloseRequest: (String?) -> Unit
) {
    Dialog(
        onCloseRequest = { onCloseRequest(null) },
        state = rememberDialogState(position = WindowPosition(Alignment.Center)),
        resizable = false,
        title = "Join"
    ) {
        val di = localDI()

        val themeService: ThemeService by di.instance()
        val theme = themeService.theme.collectAsState()

        var roomCode by remember { mutableStateOf("") }

        Column(
            modifier = Modifier.fillMaxSize().background(theme.value.background),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Group id:",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = theme.value.title
            )

            Spacer(Modifier.height(16.dp))

            InputTextField(
                value = roomCode,
                onValueChange = { roomCode = it },
            )

            Spacer(Modifier.height(16.dp))

            ColoredButton(
                text = "Join",
                color = theme.value.green,
                theme = theme.value,
                icon = null,
                onClick = { onCloseRequest(roomCode) }
            )
        }
    }
}