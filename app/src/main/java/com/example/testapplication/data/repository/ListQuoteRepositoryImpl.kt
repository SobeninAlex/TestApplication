package com.example.testapplication.data.repository

import com.example.testapplication.data.mapper.toEntity
import com.example.testapplication.data.mapper.toListEntity
import com.example.testapplication.data.network.api.ApiService
import com.example.testapplication.domain.entity.Quote
import com.example.testapplication.domain.repository.ListQuoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ListQuoteRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ListQuoteRepository {

    override fun getQuotes(offset: Int): Flow<List<Quote>> = apiService.loadQuotes()
        .map { it.toListEntity() }

}