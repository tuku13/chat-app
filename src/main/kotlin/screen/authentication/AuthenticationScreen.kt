package screen.authentication

import BASE_URL
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import theme.Theme
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import io.ktor.client.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun AuthenticationScreen(
    theme: Theme,
    client: HttpClient,
    onLogin: () -> Unit
) {
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
                    try {
                        val response: HttpResponse = client.submitForm(
                            url = "$BASE_URL/login",
                            formParameters = Parameters.build {
                                append("email", email)
                                append("password", password)
                            }
                        )

                        if(response.status.value == 200) {
                            onLogin()
                        }

                    } catch (e: Exception) {
                        println(e)
                    }
                }
            }
        )

        Spacer(modifier = Modifier.width(100.dp))

        RegisterForm(
            theme = theme,
            onSubmit = { username, email, password ->
                scope.launch(Dispatchers.IO) {
                    try {
                        val registerResponse: HttpResponse = client.submitForm(
                            url = "$BASE_URL/register",
                            formParameters = Parameters.build {
                                append("username", username)
                                append("email", email)
                                append("password", password)
                            }
                        )

                        if(registerResponse.status.value == 200) {
                            val loginResponse: HttpResponse = client.submitForm(
                                url = "$BASE_URL/login",
                                formParameters = Parameters.build {
                                    append("email", email)
                                    append("password", password)
                                }
                            )

                            if(loginResponse.status.value == 200) {
                                onLogin()
                            }
                        }

                    } catch (e: Exception) {
                        println(e)
                    }

                }
            }
        )
    }

}

@Composable
fun InputTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    theme: Theme,
    keyboardType: KeyboardType = KeyboardType.Text,
    label: String = "",
) {
    TextField(
        value = value,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType,
        ),
        visualTransformation = if (keyboardType == KeyboardType.Password) PasswordVisualTransformation() else VisualTransformation.None,
        onValueChange = onValueChange,
        modifier = modifier,
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            textColor = theme.body,
            cursorColor = theme.cursor,
            placeholderColor = theme.body,
            backgroundColor = theme.textFieldBackground,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedLabelColor = theme.title
        ),
        shape = RoundedCornerShape(0),
        label = {
            Text(
                text = label,
                color = theme.body
            )
        }
    )
}