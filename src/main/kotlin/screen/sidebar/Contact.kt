package screen.sidebar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.Room
import theme.Theme
import util.formatMessageTime

@Composable
fun Contact(
    room: Room,
    theme: Theme
) {
    val message = if (room.messages.isEmpty()) null else room.messages.last()

    // TODO kicserélni tényleges utolsó üzenetre
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(87.dp)
            .padding(top = 8.dp)
            .border(BorderStroke(1.dp, color = theme.border))
    ) {
        Row {
            Box(modifier = Modifier.padding(8.dp)) {
                NetworkImage(
                    url = "https://lh3.googleusercontent.com/2hDpuTi-0AMKvoZJGd-yKWvK4tKdQr_kLIpB_qSeMau2TNGCNidAosMEvrEXFO9G6tmlFlPQplpwiqirgrIPWnCKMvElaYgI-HiVvXc=w600",
                    modifier = Modifier.clip(CircleShape)
                )
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
                        color = theme.title
                    )
                    Text(
                        text = if (message == null) "" else formatMessageTime(room.messages.last().timestamp),
                        fontSize = 12.sp,
                        color = theme.body
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
                        color = theme.body
                    )
                }
            }
        }
    }
}