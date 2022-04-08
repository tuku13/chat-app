package model

import kotlinx.serialization.Serializable

@Serializable
data class Room(
    val id: String,
    val name: String,
    val userIds: List<String>,
    val messageIds: List<String>,
)
