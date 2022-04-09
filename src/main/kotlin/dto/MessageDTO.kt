package dto

import kotlinx.serialization.Serializable
import model.Message

@Serializable
data class MessageDTO(
    val id: String,
    val senderId: String,
    val roomId: String,
    val content: String,
    val type: String,
    val timestamp: Long
)

fun MessageDTO.toMessage(userId: String): Message {
    return Message(
        id = id,
        senderId = senderId,
        roomId = roomId,
        content = content,
        type = type,
        timestamp = timestamp,
        isReceived = senderId != userId
    )
}