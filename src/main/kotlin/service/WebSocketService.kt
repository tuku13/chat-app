package service

import dto.MessageDTO
import dto.toMessage
import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.http.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import model.Message
import util.Config

class WebSocketService(private val client: HttpClient) {
    private val _messages: MutableStateFlow<List<Message>> = MutableStateFlow(emptyList())
    val messages = _messages.asStateFlow()
    private var socket: DefaultWebSocketSession? = null

    suspend fun join(roomId: String) {
        close()
        client.webSocket(method = HttpMethod.Get, host = Config.host.split("://")[1], port = Config.port, path = "/chat/room/$roomId") {
            socket = this
            incoming.consumeEach { frame ->
                when (frame) {
                    is Frame.Text -> {
                        val text = frame.readText()

                        try {
                            val messageDTO: MessageDTO = Json.decodeFromString(text)
                            val message = messageDTO.toMessage()

                            launch { _messages.emit(_messages.value + message) }

                        } catch (e: Exception) {
                            println(e.message)
                        }

                    }
                    else -> {}
                }
            }

        }
    }

    suspend fun clearMessages() {
        _messages.emit(emptyList())
    }

    suspend fun sendImage(image: ByteArray) {
        socket?.send(image)
    }

    suspend fun sendMessage(messageText: String) {
        socket?.send(Frame.Text(messageText))
    }

    private suspend fun close() {
        socket?.close()
        _messages.emit(emptyList())
    }

}
