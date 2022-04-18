package screen.content

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ImageSearch
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.isCtrlPressed
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.compose.localDI
import org.kodein.di.instance
import screen.dialog.ImageOpenDialog
import service.ThemeService
import service.WebSocketService

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BottomBar() {
    val di = localDI()
    val scope = rememberCoroutineScope()

    val themeService: ThemeService by di.instance()
    val webSocketService: WebSocketService by di.instance()

    val theme = themeService.theme.collectAsState()

    var isFileOpenDialogOpen by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .border(BorderStroke(1.dp, color = theme.value.border))
            .onKeyEvent {
                if(it.key == Key.Enter && it.isCtrlPressed && message.isNotBlank()) {
                    val messageText = message
                    scope.launch {
                        webSocketService.sendMessage(messageText)
                    }
                    message = ""
                    return@onKeyEvent true
                }
                return@onKeyEvent false
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.Bottom,
        ) {
            Icon(
                tint = theme.value.blue,
                imageVector = Icons.Default.ImageSearch,
                contentDescription = null,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .padding(bottom = 8.dp)
                    .size(40.dp)
                    .clickable { scope.launch(Dispatchers.IO) { isFileOpenDialogOpen = true } }
            )

            if(isFileOpenDialogOpen) {
                ImageOpenDialog { file ->
                    isFileOpenDialogOpen = false
                    if(file != null) {
                        scope.launch(Dispatchers.IO) {
                            try {
                                val image = file.readBytes()
                                webSocketService.sendImage(image)
                            } catch (e: Exception) {
                                println(e.message)
                            }
                        }
                    }
                }
            }

            CompositionLocalProvider(
                LocalTextSelectionColors provides TextSelectionColors(
                    handleColor = Color.Black.copy(alpha = 0.2f),
                    backgroundColor = Color.Black.copy(alpha = 0.2f)
                )
            ) {
                TextField(
                    value = message,
                    onValueChange = { message = it },
                    modifier = Modifier
                        .weight(1.0f)
                        .border(
                            border = BorderStroke(0.dp, Color.Transparent),
                            shape = CutCornerShape(0)
                        ),
                    shape = RoundedCornerShape(0),
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = theme.value.body,
                        cursorColor = theme.value.body,
                        placeholderColor = theme.value.body,
                        backgroundColor = theme.value.background,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    placeholder = {
                        Text("Write a message")
                    }
                )
            }

            Icon(
                tint = theme.value.blue,
                imageVector = Icons.Default.Send,
                contentDescription = null,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .padding(bottom = 8.dp)
                    .size(40.dp)
                    .clickable {
                        val messageText = message
                        scope.launch {
                            webSocketService.sendMessage(messageText)
                        }
                        message = ""
                    }
            )
        }
    }
}