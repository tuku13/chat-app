package service


import dto.UserDTO
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import model.User
import model.toUserDTO
import util.Config
import util.NetworkResult

class UserService(
    private val client: HttpClient
) {
    suspend fun findUser(name: String, email: String): NetworkResult<List<User>> {
        try {
            val response: HttpResponse = client.submitForm(
                url = "${Config.baseUrl}/user",
                formParameters = Parameters.build {
                    append("name", name)
                    append("email", email)
                }
            )

            if (response.status == HttpStatusCode.OK) {
                val dto: List<UserDTO> = response.body()
                return NetworkResult.Success(dto.map { it.toUserDTO() })
            }

        } catch (e: ResponseException) {
            return NetworkResult.Error(e.response.bodyAsText(), e)
        }

        return NetworkResult.Success(emptyList())
    }

}