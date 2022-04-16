package repository

import BASE_URL
import dto.RoomDTO
import dto.toRoom
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.cookies.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import model.Room
import model.User
import service.AuthenticationService
import util.NetworkResult

class RoomRepository(
    private val client: HttpClient,
    private val authenticationService: AuthenticationService
) {
    suspend fun getRooms(): List<Room> {
        return try {
            val response: HttpResponse = client.get("$BASE_URL/rooms") {
                client.cookies("$BASE_URL/login")[0]
            }

            val roomDTOs: List<RoomDTO> = response.body()

            roomDTOs.map {
                it.toRoom(
                    client = client,
                )
            }
        } catch (e: ResponseException) {
             emptyList()
        }

    }

    suspend fun createGroup(members: List<String>, roomName: String): NetworkResult<Boolean> {
        val cookie = client.cookies("$BASE_URL/login")[0]

        try {
            val response: HttpResponse = client.submitForm(
                url = "$BASE_URL/create/room",
                formParameters = Parameters.build {
                    append("creator", authenticationService.userId)
                    append("members", members.joinToString (separator = "," ))
                    append("roomname", roomName)
                }
            ) {
                cookie
            }

            if (response.status == HttpStatusCode.OK) {
                return NetworkResult.Success(true)
            }
        } catch (e: ResponseException) {
            return NetworkResult.Error(
                message = e.response.bodyAsText(),
                exception = e
            )
        }

        return NetworkResult.Success(false)
    }

    suspend fun addContact(contactInfo: User): NetworkResult<Boolean> {
        return createGroup(
            members = listOf(contactInfo.id),
            roomName = "${authenticationService.user.value.name}, ${contactInfo.name}"
        )
    }

    suspend fun joinGroup(roomId: String): NetworkResult<Boolean> {
        try {
            val response = client.get("$BASE_URL/join/room/$roomId") {
                client.cookies("$BASE_URL/login")[0]
            }

            if(response.status == HttpStatusCode.OK) {
                return NetworkResult.Success(true)
            }

            return NetworkResult.Success(false)
        } catch (e: ResponseException) {
            return NetworkResult.Error(
                message = e.response.bodyAsText(),
                exception = e
            )
        }

    }

    suspend fun leaveGroup(roomId: String): NetworkResult<Boolean> {
        try {
            val response = client.get("$BASE_URL/leave/room/$roomId") {
                client.cookies("$BASE_URL/login")[0]
            }

            if(response.status == HttpStatusCode.OK) {
                return NetworkResult.Success(true)
            }

            return NetworkResult.Success(false)
        } catch (e: ResponseException) {
            return NetworkResult.Error(
                message = e.response.bodyAsText(),
                exception = e
            )
        }

    }
}