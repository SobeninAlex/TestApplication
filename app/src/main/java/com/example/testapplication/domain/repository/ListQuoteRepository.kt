package com.example.testapplication.domain.repository

import com.example.testapplication.domain.entity.Quote
import com.example.testapplication.domain.entity.QuoteState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface ListQuoteRepository {

    fun getQuotes(offset: Int = 1): Flow<List<Quote>>

}