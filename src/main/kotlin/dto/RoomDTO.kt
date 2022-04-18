package dto

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.cookies.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.Serializable
import model.Room
import util.Config

@Serializable
data class RoomDTO(
    val id: String,
    val name: String,
    val userIds: List<String>,
    val messageIds: List<String>,
)

suspend fun RoomDTO.toRoom(client: HttpClient): Room {
    val roomResponse: HttpResponse = client.get("${Config.baseUrl}/message/room/${id}") {
        client.cookies("${Config.baseUrl}/login")[0]
    }
    val messageDTOs: List<MessageDTO> = roomResponse.body()
    val messages = messageDTOs.map { it.toMessage() }

    return Room(
        id = id,
        name = name,
        userIds = userIds,
        messages = messages
    )
}

