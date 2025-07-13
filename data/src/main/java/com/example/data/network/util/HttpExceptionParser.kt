package com.example.data.network.util

import com.example.data.network.dto.ErrorResponseDTO
import com.google.gson.Gson
import retrofit2.HttpException

fun HttpException.parseErrorBody(): String {
    return try {

        val errorBody = this.response()?.errorBody()?.string()
        val errorResponse = Gson().fromJson(errorBody, ErrorResponseDTO::class.java)

        // In priority order exception, message, error
        errorResponse.exception
            ?: errorResponse.message
            ?: errorResponse.error
            ?: "An unknown error occurred"
    } catch (e: Exception) {
        // Fallback in case of parsing failure
        "Oops, something went wrong!"
    }
}