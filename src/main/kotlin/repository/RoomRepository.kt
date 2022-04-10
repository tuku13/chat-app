package repository

import BASE_URL
import dto.RoomDTO
import dto.UserInfoDTO
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
import service.AuthenticationService
import util.NetworkResult

class RoomRepository(
    private val client: HttpClient,
    private val authenticationService: AuthenticationService
) {

    suspend fun getRooms(): List<Room> {
        // TODO try-catch
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

    suspend fun addContact(contactInfo: UserInfoDTO):NetworkResult<Boolean> {
        val cookie = client.cookies("$BASE_URL/login")[0]

        try {
            val response: HttpResponse = client.submitForm(
                url = "$BASE_URL/create/room",
                formParameters = Parameters.build {
                    append("creator", authenticationService.userId)
                    append("members", contactInfo.id)
                    append("roomname", "${authenticationService.userInfo.value.name};${contactInfo.name}")
                }
            ) {
                cookie
            }

            if(response.status == HttpStatusCode.OK) {
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
}