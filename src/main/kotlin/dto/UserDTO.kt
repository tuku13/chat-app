package dto

import kotlinx.serialization.Serializable
import model.User

@Serializable
data class UserDTO(
    val id: String,
    val name: String,
    val email: String,
    val image: String,
) {
    fun toUser() = User(
        id = id,
        name = name,
        email = email,
        image = image
    )
}
