package com.arieftaufikrahman.doaqu.utils

sealed class ResultNetwork<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : ResultNetwork<T>(data)
    class Error<T>(message: String, data: T? = null): ResultNetwork<T>(data, message)
    class Loading<T> : ResultNetwork<T>()
}