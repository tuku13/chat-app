package screen.sidebar

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import model.Room
import theme.Theme

@Composable
fun ContactScreen(
    query: String,
    rooms: List<Room>,
    selectedRoom: Room?,
    selectRoom: (Room) -> Unit
) {
    val scrollState = rememberLazyListState()

    Box {
        LazyColumn(state = scrollState) {
            items(rooms.size) { index ->
                val room = rooms[index]
                if (room.name.lowercase().contains(query.lowercase())) {
                    Contact(
                        room = room,
                        selected = (room == selectedRoom),
                        onClick = selectRoom
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