package model

import dto.UserDTO

data class User(
    val id: String,
    val name: String,
    val email: String,
    val image: String,
)

fun UserDTO.toUserDTO() = User(
    id = id,
    name = name,
    email = email,
    image = image
)