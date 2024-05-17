package com.example.testapplication.data.repository

import com.example.testapplication.data.mapper.toListEntity
import com.example.testapplication.data.network.api.ApiService
import com.example.testapplication.domain.entity.Quote
import com.example.testapplication.domain.repository.ListQuoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ListQuoteRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ListQuoteRepository {

    override fun getQuotes(offset: Int): Flow<List<Quote>> = flow {
        val result = apiService.loadQuotes(offset = offset).toListEntity()
        if (result.isNotEmpty()) {
            emit(result)
        }
    }

}