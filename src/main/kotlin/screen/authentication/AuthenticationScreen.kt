package screen.authentication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.compose.localDI
import org.kodein.di.instance
import screen.dialog.AlertDialog
import service.ThemeService
import util.NetworkResult

@Composable
fun AuthenticationScreen() {
    val viewModel: AuthenticationViewModel by localDI().instance()

    val themeService: ThemeService by localDI().instance()
    val theme = themeService.theme.collectAsState()

    var isDialogOpen by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("Error") }

    Row(
        modifier = Modifier.background(theme.value.background).fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        val scope: CoroutineScope = rememberCoroutineScope()

        LoginForm(
            theme = theme.value,
            onSubmit = { email, password ->
                scope.launch(Dispatchers.IO) {
                    val result = viewModel.login(email, password)
                    if(result is NetworkResult.Error) {
                        isDialogOpen = true
                        dialogMessage = result.message ?: "Error"
                    }
                }
            }
        )

        if (isDialogOpen) {
            AlertDialog(
                onCloseRequest = { isDialogOpen = false },
                text = dialogMessage
            )
        }

        Spacer(modifier = Modifier.width(100.dp))

        RegisterForm(
            theme = theme.value,
            onSubmit = { username, email, password, image ->
                scope.launch(Dispatchers.IO) {
                    when(val registerResult = viewModel.register(username, email, password, image)) {
                        is NetworkResult.Success -> {
                            val loginResult = viewModel.login(email, password)
                            if(loginResult is NetworkResult.Error) {
                                isDialogOpen = true
                                dialogMessage = loginResult.message ?: "Error"
                            }
                        }
                        is NetworkResult.Error -> {
                            isDialogOpen = true
                            dialogMessage = registerResult.message ?: "Error"
                        }
                    }
                }
            }
        )
    }

}

