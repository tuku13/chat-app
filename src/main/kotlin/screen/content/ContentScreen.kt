package screen.content

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Dehaze
import androidx.compose.material.icons.filled.ImageSearch
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import theme.Theme

@Composable
fun ContentScreen(
    query: String,
    theme: Theme,
    changeTheme: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(theme.background)
    ) {
        Header(query, theme, changeTheme)
        Conversation(modifier = Modifier.weight(1.0f), theme)
        BottomBar(theme)
    }
}

@Composable
fun Header(
    query: String,
    theme: Theme,
    changeTheme: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(79.dp)
            .border(BorderStroke(1.dp, color = theme.border))
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

            Text(
                text = query,
                fontSize = 18.sp,
                color = theme.body
            )

            Icon(
                tint = theme.blue,
                imageVector = Icons.Default.Dehaze,
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clickable {
                        changeTheme()
                        println("Hamburger")
                    }
            )
        }
    }
}

@Composable
fun Conversation(
    modifier: Modifier = Modifier,
    theme: Theme
) {
    Box(
        modifier = modifier
            .border(BorderStroke(1.dp, color = theme.border))
    ) {

    }
}

@Composable
fun BottomBar(theme: Theme) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(79.dp)
            .border(BorderStroke(1.dp, color = theme.border))
    ) {
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Icon(
                tint = theme.blue,
                imageVector = Icons.Default.AttachFile,
                contentDescription = null,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .size(40.dp)
                    .clickable { println("Image") }
            )

            Icon(
                tint = theme.blue,
                imageVector = Icons.Default.ImageSearch,
                contentDescription = null,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .size(40.dp)
                    .clickable { println("GIF") }
            )

            var message by remember { mutableStateOf("") }

            TextField(
                value = message,
                onValueChange = { message = it },
                modifier = Modifier
                    .weight(1.0f)
                    .border(
                        border = BorderStroke(0.dp, Color.Transparent),
                        shape = CutCornerShape(0)
                    ),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = theme.border,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                placeholder = {
                    Text("Write a message")
                }
            )

            Icon(
                tint = theme.blue,
                imageVector = Icons.Default.Send,
                contentDescription = null,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .size(40.dp)
                    .clickable {
                        message = ""
                        println("Send")
                    }
            )
        }
    }
}
