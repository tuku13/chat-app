package util

import java.text.SimpleDateFormat
import java.util.*

fun formatMessageTime(time: Long): String = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(time))