package screen.sidebar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import image.NetworkImage
import image.ProfileImage
import model.Room
import org.kodein.di.compose.localDI
import org.kodein.di.instance
import service.ThemeService
import util.formatMessageTime

@Composable
fun Contact(
    room: Room,
    selected: Boolean,
    onClick: (Room) -> Unit
) {
    val di = localDI()
    val themeService: ThemeService by di.instance()

    val theme = themeService.theme
    val message = if (room.messages.none { it.type == "TEXT" }) null else room.messages.last { it.type == "TEXT" }
    val backgroundColor = if(selected) Color.Black.copy(alpha = 0.2f) else theme.value.background

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(87.dp)
            .padding(top = 8.dp)
            .background(backgroundColor)
            .border(BorderStroke(1.dp, color = theme.value.border))
            .clickable { onClick(room) }
    ) {
        Row {
            Box(modifier = Modifier.padding(8.dp).size(64.dp)) {
                ProfileImage(
                    userId = room.userIds[0],
                    modifier = Modifier.clip(CircleShape)
                )
//                NetworkImage(
//                    url = "https://lh3.googleusercontent.com/2hDpuTi-0AMKvoZJGd-yKWvK4tKdQr_kLIpB_qSeMau2TNGCNidAosMEvrEXFO9G6tmlFlPQplpwiqirgrIPWnCKMvElaYgI-HiVvXc=w600",
//                    modifier = Modifier.clip(CircleShape)
//                )
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(8.dp)
                    .weight(1.0f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = room.name,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = theme.value.title
                    )
                    Text(
                        text = if (message == null) "" else formatMessageTime(room.messages.last().timestamp),
                        fontSize = 12.sp,
                        color = theme.value.body
                    )
                }

                val text = when {
                    message == null -> "No messages yet."
                    message.content.length >= 60 -> "${message.content.take(60)}..."
                    else -> message.content
                }

                Box(modifier = Modifier.width(199.dp).padding(top = 8.dp)) {
                    Text(
                        maxLines = 2,
                        text = text,
                        fontSize = 14.sp,
                        color = theme.value.body
                    )
                }
            }
        }
    }
}