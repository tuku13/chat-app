package service

import BASE_URL
import dto.UserDTO
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.cookies.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import model.User
import model.toUserDTO
import util.NetworkResult

class AuthenticationService(
    private val client: HttpClient
) {
    private var _authenticated: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val authenticated: StateFlow<Boolean>
        get() = _authenticated

    private var _userId: String = ""
    val userId
        get() = _userId

    private var _user: MutableStateFlow<User> = MutableStateFlow(User("", "", "", ""))
    val user: StateFlow<User>
        get() = _user


    suspend fun login(email: String, password: String): NetworkResult<Boolean> {
        try {
            val response: HttpResponse = client.submitForm(
                url = "$BASE_URL/login",
                formParameters = Parameters.build {
                    append("email", email)
                    append("password", password)
                }
            )

            if (response.status.value == 200) {
                println(authenticated.value)
                _authenticated.emit(true)
                println(authenticated.value)
                _userId = response.body()

                val userDTO: UserDTO = client.post("$BASE_URL/user/$_userId").body()
                _user.emit(userDTO.toUser())

                return NetworkResult.Success(true)
            }

        } catch (e: ResponseException) {
            return NetworkResult.Error(
                message = e.response.bodyAsText(),
                exception = e
            )
        }

        return NetworkResult.Success(true)
    }

    suspend fun register(username: String, email: String, password: String, image: String): NetworkResult<Boolean> {
        try {
            val response: HttpResponse = client.submitForm(
                url = "$BASE_URL/register",
                formParameters = Parameters.build {
                    append("username", username)
                    append("email", email)
                    append("password", password)
                    append("image", image)
                }
            )

            if (response.status.value == 200) {
                return NetworkResult.Success(true)
            }

        } catch (e: Exception) {
            return NetworkResult.Error(e.message, e)
        }
        return NetworkResult.Error(
            message = "Unknown error",
            exception = java.lang.Exception("Unknown error")
        )
    }

    suspend fun logout(): NetworkResult<Boolean> {
        try {
            val response: HttpResponse = client.get("$BASE_URL/logout") {
                client.cookies("$BASE_URL/login")[0]
            }

            if(response.status == HttpStatusCode.OK) {
                _authenticated.emit(false)
                return NetworkResult.Success(true)
            }

        } catch (e: Exception) {
            return NetworkResult.Error(
                message = e.message,
                exception = e
            )
        }

        return NetworkResult.Success(false)
    }
}