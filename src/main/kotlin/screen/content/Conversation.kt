package screen.content

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import model.Message
import model.Room
import pollMessages

@Composable
fun Conversation(
    modifier: Modifier = Modifier,
    selectedRoom: Room?
) {
    Box(
        modifier = modifier
    ) {
        var messages by remember { mutableStateOf<List<Message>>(emptyList()) }
        val scrollState = rememberLazyListState()

        LaunchedEffect(Any()) {
            pollMessages().collect {
                messages = listOf(*messages.toTypedArray(), it)
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