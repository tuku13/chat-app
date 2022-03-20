package screen.content

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.Message
import screen.sidebar.NetworkImage
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
    ) {
        var messages = remember {
            listOf(
                Message(
                    username = "Pityu",
                    text = "dsf8942j789fh27q893hg2g"
                ),
                Message(
                    username = "Béla",
                    text = "dsf8942jdfgdsfgfhdzqurevhinzegh677777777777792bm68hsgfghw45rt789fh27q893hg2g"
                ),
                Message(
                    username = "Attla",
                    text = "dsf8dfgs942j7dsf8dfgs942j7dfsdfgg8sdfg9fh27q893hg2dsf8dfgs942j7dfsdfgg8sdfg9fh27q893hg2dfsdfgg8sdfg9fh27q893hg2g"
                ),
                Message(
                    username = "Attila",
                    text = "d"
                ),
                Message(
                    username = "Attila",
                    text = "dsf8dfgs942j7dfdfghdfghdfghfghfgdhfdghfgdhfgdhddfghdfghdfghfghfgdhfdghfgdhfgdhddfghdfghdfghfghfgdhfdghfgdhfgdhdsdfgg8sdfg9fh27q893hg2g"
                ),
                Message(
                    username = "Attila",
                    text = "dsf8dfgs942j7dfghddfghdfghdfghfghfgdhfdghfgdhfgdhddfghdfghdfghfghfgdhfdghfgdhfgdhdfghdfghfghfgdhfdghfgdhfgdhdfsdfgg8sdfg9fh27q893hg2g"
                ),
                Message(
                    username = "Attfgdhila",
                    text = "dsf8dfgs942j7dfsdfgg8fgdhfdghgdfhgfhsdfg9fh27q893hg2g"
                ),
                Message(
                    username = "Atgfdhla",
                    text = "dsf8dfgs942j7dfsdfgg8sdfg9fh27q893hg2g"
                ),
                Message(
                    username = "Cukorhegyi Márk",
                    text = "Meta, metamates, me"
                ),
                Message(
                    username = "Én",
                    text = "\uD83D\uDE33"
                ),

            )
        }

        LazyColumn(
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            items(messages.size) { index ->
                val message = messages[index]

                ChatBubble(
                    message = message,
                    theme = theme,
                    own = !message.username.contains("i")
                )
            }
        }

    }
}

@Composable
fun ChatBubble(
    message: Message,
    own: Boolean = false,
    theme: Theme
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {

        if (own) {
            Box(modifier = Modifier.height(50.dp).weight(15.0f))
        }

        Row {
            if(!own) {
                NetworkImage(
                    url = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ1ndP7V2fYnKkB8UQzMhZFX-joFjrGvNoCGw&usqp=CAU",
                    modifier = Modifier
                        .padding(8.dp)
                        .size(40.dp)
                        .clip(RoundedCornerShape(100.0f))
                        .align(Alignment.Bottom)
                )
            }

            Box(
                modifier = Modifier
                    .wrapContentHeight()
                    .weight(85.0f),
            ) {
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .align(
                            if (own) Alignment.CenterEnd else Alignment.CenterStart
                        ),
                    backgroundColor = if (own) theme.green else theme.chatBackground,
                    shape = RoundedCornerShape(10.dp),
                ) {
                    Column() {
                        Box(modifier = Modifier.height(8.dp))
                        if (!own) {
                            Text(
                                text = message.username,
                                color = theme.title,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }

                        Text(
                            text = message.text,
                            fontSize = 14.sp,
                            color = if(own) Color.Black else theme.chatText,
                            modifier = Modifier
                                .padding(horizontal = 8.dp,)
                        )

                        Text(
                            text = "14:36",
                            fontSize = 9.sp,
                            color = theme.body,
                            modifier = Modifier
                                .padding(8.dp)
                                .align(Alignment.End)
                        )

                    }
                }
            }
        }


        if (!own) {
            Box(modifier = Modifier.height(50.dp).weight(15.0f))
        }
    }
}

@Composable
fun BottomBar(theme: Theme) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .border(BorderStroke(1.dp, color = theme.border))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
//                .padding(vertical = 16.dp)
                .wrapContentHeight(),
            verticalAlignment = Alignment.Bottom,
        ) {

            Icon(
                tint = theme.blue,
                imageVector = Icons.Default.AttachFile,
                contentDescription = null,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .padding(bottom = 8.dp)
                    .size(40.dp)
                    .clickable { println("Image") }
            )

            Icon(
                tint = theme.blue,
                imageVector = Icons.Default.ImageSearch,
                contentDescription = null,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .padding(bottom = 8.dp)
                    .size(40.dp)
                    .clickable { println("GIF") }
            )

            var message by remember { mutableStateOf("") }

            CompositionLocalProvider(
                LocalTextSelectionColors provides TextSelectionColors(
                    handleColor = theme.blue,
                    backgroundColor = theme.blue
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
                        textColor = theme.body,
                        cursorColor = theme.border,
                        placeholderColor = theme.body,
                        backgroundColor = theme.background,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    placeholder = {
                        Text("Write a message")
                    }
                )
            }

            Icon(
                tint = theme.blue,
                imageVector = Icons.Default.Send,
                contentDescription = null,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .padding(bottom = 8.dp)
                    .size(40.dp)
                    .clickable {
                        message = ""
                        println("Send")
                    }
            )
        }
    }
}
