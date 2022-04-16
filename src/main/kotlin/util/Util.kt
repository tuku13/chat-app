package util

import BASE_URL
import io.ktor.client.*
import io.ktor.client.plugins.cookies.*
import java.text.SimpleDateFormat
import java.util.*

fun formatMessageTime(time: Long): String = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(time))

suspend fun HttpClient.sessionCookie() = cookies("$BASE_URL/login")[0]