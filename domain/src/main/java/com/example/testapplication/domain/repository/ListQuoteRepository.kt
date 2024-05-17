package com.example.testapplication.domain.repository

import com.example.testapplication.domain.entity.Quote
import kotlinx.coroutines.flow.Flow

interface ListQuoteRepository {

    fun getQuotes(offset: Int = 1): Flow<List<Quote>>

}