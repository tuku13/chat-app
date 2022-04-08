package repository

import BASE_URL
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

        return response.body()
    }

}