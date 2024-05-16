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

//    private val _listQuotes = mutableListOf<Quote>()
//    private val listQuotes get() = _listQuotes.toList()

    override fun getQuotes(offset: Int): Flow<List<Quote>> = flow {
        val result = apiService.loadQuotes(offset = offset).toListEntity()
        if (result.isNotEmpty()) {
            emit(result)
        }
//        val isLoadingMore = result.size == 10
//        _listQuotes.addAll(result)
//        val quoteState = QuoteState(isLoadingMore = isLoadingMore, quotes = listQuotes)
    }

}