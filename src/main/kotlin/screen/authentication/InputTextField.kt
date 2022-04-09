package screen.authentication

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import org.kodein.di.compose.localDI
import org.kodein.di.instance
import service.ThemeService

@Composable
fun InputTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    label: String = "",
) {
    val themeService: ThemeService by localDI().instance()
    val theme = themeService.theme.collectAsState().value

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