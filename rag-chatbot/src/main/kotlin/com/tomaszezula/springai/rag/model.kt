package com.tomaszezula.springai.rag

sealed interface ServiceResponse<out T> {
    data class Success<T>(val data: T) : ServiceResponse<T>
    data class Failure(val reason: String) : ServiceResponse<Nothing>
}

fun<T> call(block: () -> ServiceResponse<T>): T {
    return when(val response = block()) {
        is ServiceResponse.Success -> response.data
        is ServiceResponse.Failure -> throw RuntimeException(response.reason)
    }
}
