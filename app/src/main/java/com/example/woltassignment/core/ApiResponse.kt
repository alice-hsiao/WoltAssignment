package com.example.woltassignment.core


sealed interface ApiResponse<out SUCCESS, out ERROR> {
    data class Success<SUCCESS>(val response: SUCCESS) : ApiResponse<SUCCESS, Nothing>

    sealed class Fail<ERROR> : ApiResponse<Nothing, ERROR> {
        data class HttpError<ERROR>(val statusCode: Int, val body: ERROR) : Fail<ERROR>()
        data object NetworkError : Fail<Nothing>()
        data object SerializationError : Fail<Nothing>()
        data object Unknown : Fail<Nothing>()
    }
}
