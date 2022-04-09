package repository

import BASE_URL
import dto.RoomDTO
import dto.toRoom
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.cookies.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import model.Room

class RoomRepository(
    private val client: HttpClient
) {

    suspend fun getRooms(): List<Room> {
        val response: HttpResponse = client.get("$BASE_URL/rooms") {
            client.cookies("$BASE_URL/login")[0]
        }

        val roomDTOs: List<RoomDTO> = response.body()

        return roomDTOs.map {
            it.toRoom(
                client = client,
                userId = "624f2c95fce1a1538a285cd1"
            )
        }
    }

    suspend fun createRoom() {
        val response: HttpResponse = client.get("$BASE_URL/create/room") {
            client.cookies("$BASE_URL/login")[0]
        }
    }
}