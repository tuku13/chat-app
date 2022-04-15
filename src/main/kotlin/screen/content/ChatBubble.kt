package screen.content

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sun.org.apache.xalan.internal.lib.ExsltStrings.padding
import model.Message
import org.kodein.di.compose.localDI
import org.kodein.di.instance
import screen.sidebar.LocalImage
import screen.sidebar.NetworkImage
import service.ThemeService
import theme.Theme
import util.formatMessageTime
import java.util.*

@Composable
fun ChatBubble(message: Message) {
    val di = localDI()

    val themeService: ThemeService by di.instance()

    val theme = themeService.theme



    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (message.isReceived) Arrangement.Start else Arrangement.End
    ) {

        if (!message.isReceived) {
            Box(modifier = Modifier.height(50.dp).weight(15.0f))
        }

        Row {
            if (message.isReceived) {
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
                    .fillMaxWidth(0.85f),
            ) {
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .align(
                            if (message.isReceived) Alignment.CenterStart else Alignment.CenterEnd
                        ),
                    backgroundColor = if (!message.isReceived) theme.value.green else theme.value.chatBackground,
                    shape = RoundedCornerShape(10.dp),
                ) {
                    Column {
                        Box(modifier = Modifier.height(8.dp))

                        if (message.isReceived) {
                            Text(
                                text = message.senderName,
                                color = theme.value.title,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )
                        }

                        when(message.type) {
                            "IMAGE" -> {
                                val uuid = UUIDFromString(message.content)
                                if(uuid != UUID.fromString("")) {
                                    LocalImage(
                                        uuid = uuid,
                                        modifier = Modifier.padding(16.dp).widthIn(max = 512.dp).heightIn(max = 512.dp)
                                    )
                                }
                            }
                            else -> {
                                Text(
                                    text = message.content,
                                    fontSize = 14.sp,
                                    color = theme.value.chatText,
                                    modifier = Modifier.padding(horizontal = 8.dp)
                                )
                            }
                        }

                        Text(
                            text = formatMessageTime(message.timestamp),
                            fontSize = 9.sp,
                            color = theme.value.body,
                            modifier = Modifier
                                .padding(8.dp)
                                .align(Alignment.End)
                        )

                    }
                }
            }
        }

//        if (!message.isReceived) {
//            Box(modifier = Modifier.height(50.dp).weight(15.0f))
//        }
    }
}

fun UUIDFromString(uuidString: String): UUID {
    return try {
        UUID.fromString(uuidString)
    } catch (e: Exception) {
        return UUID.randomUUID()
    }
}