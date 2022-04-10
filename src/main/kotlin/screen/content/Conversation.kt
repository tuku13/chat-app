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
import org.kodein.di.compose.localDI
import org.kodein.di.instance
import pollMessages
import screen.main.MainViewModel

@Composable
fun Conversation(
    modifier: Modifier = Modifier,
    selectedRoom: Room?
) {
    Box(
        modifier = modifier
    ) {
        val messages = selectedRoom?.messages ?: emptyList()
        var realTimeMessages by remember { mutableStateOf<List<Message>>(emptyList()) }
        val scrollState = rememberLazyListState()

        LaunchedEffect(Any()) {
            pollMessages().collect {
                realTimeMessages = listOf(*realTimeMessages.toTypedArray(), it)
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

                items(realTimeMessages.size) { index ->
                    val message = realTimeMessages[index]
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