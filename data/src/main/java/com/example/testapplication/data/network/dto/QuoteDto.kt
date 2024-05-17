package com.example.testapplication.data.network.dto

import com.google.gson.annotations.SerializedName

data class QuoteDto(
    @SerializedName("id") val id: Int,
    @SerializedName("createdBy") val createdBy: Int,
    @SerializedName("text") val text: String
)
