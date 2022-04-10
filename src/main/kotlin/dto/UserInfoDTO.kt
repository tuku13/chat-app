package dto

import kotlinx.serialization.Serializable

@Serializable
data class UserInfoDTO(
    val id: String?,
    val name: String?,
    val email: String?
)
