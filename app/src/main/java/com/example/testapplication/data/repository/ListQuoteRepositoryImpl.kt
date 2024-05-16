package com.example.testapplication.data.repository

import android.util.Log
import com.example.testapplication.data.mapper.toEntity
import com.example.testapplication.data.mapper.toListEntity
import com.example.testapplication.data.network.api.ApiService
import com.example.testapplication.domain.entity.Quote
import com.example.testapplication.domain.repository.ListQuoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ListQuoteRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ListQuoteRepository {

    private val _quotes = mutableListOf<Quote>()

    private val _loadNext = MutableStateFlow(true)
    override val loadNext get() = _loadNext.asStateFlow()

    override suspend fun getQuotes(offset: Int): List<Quote> {
        val result = apiService.loadQuotes(offset = offset).toListEntity()
        Log.d("ListQuoteRepositoryImpl", result.toString())
        if (result.size < 10) {
            _loadNext.value = false
        }
        _quotes.addAll(result)
        return _quotes.toList()
    }

}