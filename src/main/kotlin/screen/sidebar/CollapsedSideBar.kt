package screen.sidebar

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DoubleArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import image.NetworkImage
import image.ProfileImage
import model.Room
import org.kodein.di.compose.localDI
import org.kodein.di.instance
import service.ThemeService

@Composable
fun CollapsedSideBar(
    collapseIconOnClick: () -> Unit,
    rooms: List<Room>,
    selectedRoom: Room?,
    selectRoom: (Room) -> Unit
) {
    val di = localDI()
    val themeService: ThemeService by di.instance()

    val scrollState = rememberLazyListState()
    val theme = themeService.theme

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(79.dp)
            .background(theme.value.background)
            .border(BorderStroke(1.dp, color = theme.value.border)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .height(79.dp)
                .fillMaxWidth()
                .border(BorderStroke(1.dp, color = theme.value.border))
                .align(Alignment.CenterHorizontally)
                .clickable { collapseIconOnClick() }
        ) {
            Icon(
                imageVector = Icons.Default.DoubleArrow,
                tint = theme.value.blue,
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.Center)
            )
        }

        Box(modifier = Modifier.fillMaxWidth().weight(1.0f)) {
            LazyColumn(
                verticalArrangement = Arrangement.Center,
                state = scrollState
            ) {
                items(rooms.size) { index ->
                    val room = rooms[index]
                    val backgroundColor = if (room == selectedRoom) Color.Black.copy(alpha = 0.2f) else theme.value.background

                    Spacer(modifier = Modifier.height(8.dp))

                    Box(
                        modifier = Modifier
                            .clickable { selectRoom(room) }
                            .border(BorderStroke(1.dp, theme.value.border))
                            .background(backgroundColor)
                            .padding(8.dp)
                            .size(64.dp)
                    ) {
                        ProfileImage(
                            userId = room.userIds[0],
                            modifier = Modifier.clip(CircleShape)
                        )
                    }
                }
            }

            VerticalScrollbar(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .fillMaxHeight(),
                adapter = rememberScrollbarAdapter(
                    scrollState = scrollState
                )
            )
        }
    }
}