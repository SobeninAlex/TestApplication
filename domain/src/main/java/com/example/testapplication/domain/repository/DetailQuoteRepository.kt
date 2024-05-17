package com.example.testapplication.domain.repository

import com.example.testapplication.domain.entity.Quote

interface DetailQuoteRepository {

    suspend fun getQuote(quoteId: Int): Quote

}