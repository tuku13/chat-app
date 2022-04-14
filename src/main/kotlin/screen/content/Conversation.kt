package screen.content

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import model.Room
import org.kodein.di.compose.localDI
import org.kodein.di.instance
import service.WebSocketService

@Composable
fun Conversation(
    modifier: Modifier = Modifier,
    selectedRoom: Room?
) {
    Box(
        modifier = modifier
    ) {
        val di = localDI()

        val webSocketService: WebSocketService by di.instance()

        val messages = selectedRoom?.messages ?: emptyList()
        val realTimeMessages = webSocketService.messages.collectAsState()
        val scrollState = rememberLazyListState()

        LaunchedEffect(selectedRoom) {
            selectedRoom?.let { webSocketService.join(it.id) }
        }

        LaunchedEffect(scrollState) {
            snapshotFlow { scrollState.layoutInfo.totalItemsCount }
                .collect {
                    scrollState.animateScrollToItem(it)
                }
        }

        Box {
            LazyColumn(
                modifier = Modifier.align(Alignment.BottomCenter),
                state = scrollState
            ) {

                items(messages.size) { index ->
                    val message = messages[index]
                    ChatBubble(message)
                }

                items(realTimeMessages.value.size) { index ->
                    val message = realTimeMessages.value[index]
                    ChatBubble(message)
                }
            }

            VerticalScrollbar(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .fillMaxHeight(),
                adapter = rememberScrollbarAdapter(scrollState = scrollState)
            )
        }

    }
}