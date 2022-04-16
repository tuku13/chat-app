package model

data class Room(
    val id: String,
    val name: String,
    val userIds: List<String>,
    val messages: List<Message>
)
