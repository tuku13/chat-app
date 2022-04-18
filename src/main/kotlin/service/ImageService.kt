package service


import dto.UserDTO
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import util.Config
import util.sessionCookie

class ImageService(
    private val client: HttpClient
) {
    private val images: MutableMap<String, String> = mutableMapOf()

    suspend fun getImage(userId: String): String? {
        images[userId]?.let { return it }
        val cookie = client.sessionCookie()

        try {
            val response: HttpResponse = client.submitForm(url = "${Config.baseUrl}/user/${userId}") {
                cookie
            }

            if(response.status == HttpStatusCode.OK) {
                val userDTO: UserDTO = response.body()

                images[userId] = userDTO.image

                return userDTO.image
            }
        } catch (e: ResponseException) {
            println(e.message)
        }

        return null
    }
}