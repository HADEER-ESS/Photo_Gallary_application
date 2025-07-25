package com.hadeer.domain

import java.io.IOException

sealed interface NetworkResponse <out T:Any>{
    data class Success<T : Any>(val body: T) : NetworkResponse<T>

    /**
     * Failure response with body
     */
    data class ApiError(val body: String, val code: Int) : NetworkResponse<Nothing>

    /**
     * Network error
     */
    data class NetworkError(val error: IOException) : NetworkResponse<Nothing>

    /**
     * For example, json parsing error
     */
    data class UnknownError(val error: Throwable) : NetworkResponse<Nothing>
}