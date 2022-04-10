package model

import dto.UserInfoDTO

data class UserInfo(
    val id: String,
    val name: String,
    val email: String
)

fun UserInfoDTO.toUserInfo() = UserInfo(
    id = id ?: "",
    name = name ?: "",
    email = email ?: ""
)