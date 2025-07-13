package com.example.data.network.dto

import com.google.gson.annotations.SerializedName

data class ErrorResponseDTO(

    // For any type of error response from the server

    @SerializedName("message")
    val message: String?,
    @SerializedName("exception")
    val exception: String?,
    @SerializedName("error")
    val error: String?
)