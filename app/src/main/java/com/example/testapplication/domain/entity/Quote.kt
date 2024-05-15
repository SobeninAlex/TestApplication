package com.example.testapplication.domain.entity

data class Quote(
    val id: Int,
    val createdAt: String = "",
    val createdBy: Int,
    val text: String,
    val tags: List<String> = listOf(),
    val colors: List<String> = listOf(),
)
