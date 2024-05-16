package com.example.testapplication.domain.repository

import com.example.testapplication.domain.entity.Quote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface ListQuoteRepository {

    suspend fun getQuotes(offset: Int = 1): List<Quote>

    val loadNext : StateFlow<Boolean>

}