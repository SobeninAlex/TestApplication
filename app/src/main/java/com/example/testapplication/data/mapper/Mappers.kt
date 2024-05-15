package com.example.testapplication.data.mapper

import com.example.testapplication.data.network.dto.QuoteDetailDto
import com.example.testapplication.data.network.dto.QuoteDto
import com.example.testapplication.domain.entity.Quote

fun QuoteDto.toEntity(): Quote =
    Quote(
        id = this.id,
        createdBy = this.createdBy,
        text = this.text
    )

fun List<QuoteDto>.toListEntity(): List<Quote> = map { it.toEntity() }

fun QuoteDetailDto.toEntity(): Quote =
    Quote(
        id = id,
        createdAt = createdAt,
        createdBy = createdBy,
        text = text,
        tags = tagList,
        colors = colors
    )