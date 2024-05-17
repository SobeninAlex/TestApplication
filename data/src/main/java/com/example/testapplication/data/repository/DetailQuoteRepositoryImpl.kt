package com.example.testapplication.data.repository

import com.example.testapplication.data.mapper.toEntity
import com.example.testapplication.data.network.api.ApiService
import com.example.testapplication.domain.entity.Quote
import com.example.testapplication.domain.repository.DetailQuoteRepository
import javax.inject.Inject

class DetailQuoteRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : DetailQuoteRepository {

    override suspend fun getQuote(quoteId: Int): Quote =
        apiService.loadQuoteDetail(id = quoteId).toEntity()

}