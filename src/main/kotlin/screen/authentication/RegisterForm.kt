package screen.authentication

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import screen.dialog.ImageOpenDialog
import image.LocalImage
import theme.Theme
import java.util.*

@Composable
fun RegisterForm(
    theme: Theme,
    onSubmit: (String, String, String, String) -> Unit
) {
    Column(
        modifier = Modifier
            .background(theme.background)
            .wrapContentSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var username by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        var imageBase64Encoded by remember { mutableStateOf("") }
        var isFileOpenDialogOpen by remember { mutableStateOf(false) }

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
                    text = "Register",
                    modifier = Modifier.align(Alignment.Start).padding(bottom = 16.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = theme.title
                )

                InputTextField(
                    value = username,
                    onValueChange = { username = it },
                    modifier = Modifier.padding(bottom = 16.dp),
                    label = "Username"
                )

                InputTextField(
                    value = email,
                    onValueChange = { email = it },
                    modifier = Modifier.padding(bottom = 16.dp),
                    keyboardType = KeyboardType.Email,
                    label = "Email"
                )

                InputTextField(
                    value = password,
                    onValueChange = { password = it },
                    modifier = Modifier.padding(bottom = 16.dp),
                    keyboardType = KeyboardType.Password,
                    label = "Password"
                )

                Row(
                    modifier = Modifier.padding(bottom = 16.dp).background(theme.background),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .background(theme.textFieldBackground)
                            .size(40.dp)
                    ) {
                        LocalImage(
                            byteArray = Base64.getDecoder().decode(imageBase64Encoded.toByteArray()),
                            modifier = Modifier.clip(CircleShape)
                        )
                    }

                    Button(
                        modifier = Modifier.padding(start = 32.dp),
                        onClick = { isFileOpenDialogOpen = true },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = theme.blue,
                            contentColor = theme.title
                        )
                    ) {
                        Text(
                            text = "Upload Profile Picture",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = theme.title
                        )
                    }

                }

                if(isFileOpenDialogOpen) {
                    ImageOpenDialog { file ->
                        isFileOpenDialogOpen = false
                        if(file != null) {
                            imageBase64Encoded = Base64.getEncoder().encodeToString(file.readBytes())
                        }
                    }
                }

                Button(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    onClick = { onSubmit(username, email, password, imageBase64Encoded) },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = theme.green,
                        contentColor = theme.title
                    )
                ) {
                    Text(
                        text = "Register",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = theme.title
                    )
                }
            }
        }
    }
}