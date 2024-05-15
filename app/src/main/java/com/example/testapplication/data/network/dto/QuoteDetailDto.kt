package com.example.testapplication.data.network.dto

import com.google.gson.annotations.SerializedName

data class QuoteDetailDto(
    @SerializedName("id") val id: Int,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("createdBy") val createdBy: Int,
    @SerializedName("text") val text: String,
    @SerializedName("tagList") val tagList: List<String>,
    @SerializedName("colors") val colors: List<String>,
)
