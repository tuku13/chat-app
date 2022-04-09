package screen.authentication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.compose.localDI
import org.kodein.di.instance
import service.ThemeService

@Composable
fun AuthenticationScreen() {
    val viewModel: AuthenticationViewModel by localDI().instance()

    val themeService: ThemeService by localDI().instance()
    val theme = themeService.theme.collectAsState().value

    Row(
        modifier = Modifier.background(theme.background).fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        val scope: CoroutineScope = rememberCoroutineScope()

        LoginForm(
            theme = theme,
            onSubmit = { email, password ->
                scope.launch(Dispatchers.IO) {
                    viewModel.login(email, password)
                }
            }
        )

        Spacer(modifier = Modifier.width(100.dp))

        RegisterForm(
            theme = theme,
            onSubmit = { username, email, password ->
                scope.launch(Dispatchers.IO) {
                    val registerSuccessful = viewModel.register(username, email, password)
                    if (registerSuccessful) {
                        viewModel.login(email, password)
                    }
                }
            }
        )
    }

}

