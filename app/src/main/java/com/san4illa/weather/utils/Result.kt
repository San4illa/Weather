package com.san4illa.weather.utils

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class Result<out R> {

    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }

    fun handle(onSuccess: (R) -> Unit = {}, onError: (Exception) -> Unit = {}) {
        when (this) {
            is Success -> onSuccess(this.data)
            is Error -> onError(this.exception)
        }
    }
}