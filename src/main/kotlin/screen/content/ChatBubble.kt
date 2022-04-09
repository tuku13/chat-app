package screen.content

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.Message
import screen.sidebar.NetworkImage
import theme.Theme

@Composable
fun ChatBubble(
    message: Message,
    theme: Theme
) {

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {

        if (message.isReceived) {
            Box(modifier = Modifier.height(50.dp).weight(15.0f))
        }

        Row {
            if (!message.isReceived) {
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
                            if (message.isReceived) Alignment.CenterEnd else Alignment.CenterStart
                        ),
                    backgroundColor = if (message.isReceived) theme.green else theme.chatBackground,
                    shape = RoundedCornerShape(10.dp),
                ) {
                    Column {
                        Box(modifier = Modifier.height(8.dp))

                        if (!message.isReceived) {
                            Text(
                                text = message.senderId,
                                color = theme.title,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }

                        Text(
                            text = message.content,
                            fontSize = 14.sp,
                            color = theme.chatText,
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                        )

                        Text(
                            text = message.timestamp.toString(),
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


        if (!message.isReceived) {
            Box(modifier = Modifier.height(50.dp).weight(15.0f))
        }
    }
}