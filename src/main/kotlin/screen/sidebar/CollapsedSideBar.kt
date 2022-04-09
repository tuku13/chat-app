package screen.sidebar

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DoubleArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import model.Room
import org.kodein.di.compose.localDI
import org.kodein.di.instance
import service.ThemeService
import theme.Theme

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
    val theme = themeService.theme.value

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(79.dp)
            .background(theme.background)
            .border(BorderStroke(1.dp, color = theme.border)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .height(79.dp)
                .fillMaxWidth()
                .border(BorderStroke(1.dp, color = theme.border))
                .align(Alignment.CenterHorizontally)
                .clickable { collapseIconOnClick() }
        ) {
            Icon(
                imageVector = Icons.Default.DoubleArrow,
                tint = theme.blue,
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
                    val backgroundColor = if (room == selectedRoom) Color.Black.copy(alpha = 0.2f) else theme.background

                    Spacer(modifier = Modifier.height(8.dp))

                    Box(
                        modifier = Modifier
                            .clickable { selectRoom(room) }
                            .border(BorderStroke(1.dp, theme.border))
                            .background(backgroundColor)
                            .padding(8.dp)
                    ) {
                        NetworkImage(
                            url = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTvBvDyztL89oD6gnCtsAI8WEd2mo_3TBcWff3wPPGxE2R-6D0ZiZcCmcO9InCciDDgwjs&usqp=CAU",
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