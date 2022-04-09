package model

data class Message(
    val id: String,
    val senderId: String,
    val roomId: String,
    val content: String,
    val type: String,
    val timestamp: Long,
    val isReceived: Boolean
)

