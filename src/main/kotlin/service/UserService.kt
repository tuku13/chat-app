package service

import BASE_URL
import dto.UserInfoDTO
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import util.NetworkResult

class UserService(
    private val client: HttpClient
) {

    suspend fun findUser(name: String, email: String) : NetworkResult<List<UserInfoDTO>> {
        try {
            val response: HttpResponse = client.submitForm(
                url = "$BASE_URL/user",
                formParameters = Parameters.build {
                    append("name", name)
                    append("email", email)
                }
            )

            if (response.status == HttpStatusCode.OK) {
                return NetworkResult.Success(response.body())
            }

        } catch (e: ResponseException) {
            return NetworkResult.Error(e.response.bodyAsText(), e)
        }

        return NetworkResult.Success(emptyList())
    }
}