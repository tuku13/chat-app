package dto

import di.DIContainer
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import kotlinx.serialization.Serializable
import model.Message
import model.toUserDTO
import org.kodein.di.instance
import service.AuthenticationService
import util.Config

@Serializable
data class MessageDTO(
    val id: String,
    val senderId: String,
    val roomId: String,
    val content: String,
    val type: String,
    val timestamp: Long
)

suspend fun MessageDTO.toMessage(): Message {
    val client: HttpClient by DIContainer.di.instance()
    val authenticationService: AuthenticationService by DIContainer.di.instance()

    val userId = authenticationService.userId
    var senderName = ""

    try {
        val userDTO: UserDTO = client.post("${Config.baseUrl}/user/${senderId}").body()

        senderName = userDTO.toUserDTO().name

    } catch (e: ResponseException) {
        println("Error converting $this ti Message, Exception: ${e.message}")
    }

    return Message(
        id = id,
        senderId = senderId,
        senderName = senderName,
        roomId = roomId,
        content = content,
        type = type,
        timestamp = timestamp,
        isReceived = senderId != userId
    )
}