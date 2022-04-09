package dto

import BASE_URL
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.cookies.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.Serializable
import model.Room

@Serializable
data class RoomDTO(
    val id: String,
    val name: String,
    val userIds: List<String>,
    val messageIds: List<String>,
)

suspend fun RoomDTO.toRoom(client: HttpClient, userId: String): Room {
    val response: HttpResponse = client.get("$BASE_URL/message/room/${id}") {
        client.cookies("$BASE_URL/login")[0]
    }

    val messageDTOs: List<MessageDTO> = response.body()

    val messages = messageDTOs.map { it.toMessage(userId) }

    return Room(
        id = id,
        name = name,
        userIds = userIds,
        messages = messages
    )
}

