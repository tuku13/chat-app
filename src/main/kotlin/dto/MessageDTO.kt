package dto

import BASE_URL
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.serialization.Serializable
import model.Message
import model.toUserInfo

@Serializable
data class MessageDTO(
    val id: String,
    val senderId: String,
    val roomId: String,
    val content: String,
    val type: String,
    val timestamp: Long
)

suspend fun MessageDTO.toMessage(userId: String, client: HttpClient): Message {
    val userInfoDTO: UserInfoDTO = client.post("$BASE_URL/user/${senderId}").body()

    return Message(
        id = id,
        senderId = senderId,
        senderName = userInfoDTO.toUserInfo().name,
        roomId = roomId,
        content = content,
        type = type,
        timestamp = timestamp,
        isReceived = senderId != userId
    )
}