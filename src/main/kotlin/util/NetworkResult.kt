package util

import java.lang.Exception

sealed class NetworkResult<out T> {
    data class Success<out T>(val value: T) : NetworkResult<T>()
    data class Error(
        val message: String?,
        val exception: Exception
    ) : NetworkResult<Nothing>()
}