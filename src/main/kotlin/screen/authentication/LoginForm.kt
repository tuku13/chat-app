package screen.authentication

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.*
import theme.Theme

@Composable
fun LoginForm(
    theme: Theme,
    onSubmit: (String, String) -> Unit
) {
    Column(
        modifier = Modifier
            .background(theme.background)
            .wrapContentSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var email by remember { mutableStateOf("pece@email.com") }
        var password by remember { mutableStateOf("password") }

        Card(
            elevation = 1.dp,
            shape = RoundedCornerShape(5.dp),
            border = BorderStroke(
                width = 1.dp,
                color = theme.body
            ),
        ) {
            Column(
                modifier = Modifier.wrapContentWidth().background(theme.background).padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Login",
                    modifier = Modifier.align(Alignment.Start).padding(bottom = 16.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = theme.title
                )

                InputTextField(
                    value = email,
                    onValueChange = { email = it },
                    modifier = Modifier.padding(bottom = 16.dp),
                    label = "Email"
                )

                InputTextField(
                    value = password,
                    onValueChange = { password = it },
                    modifier = Modifier.padding(bottom = 16.dp),
                    keyboardType = KeyboardType.Password,
                    label = "Password"
                )

                Button(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    onClick = { onSubmit(email, password) },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = theme.green,
                        contentColor = theme.title
                    )
                ) {
                    Text(
                        text = "Login",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = theme.title
                    )
                }
            }
        }
    }
}