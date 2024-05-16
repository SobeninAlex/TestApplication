package com.example.testapplication.data.repository

import android.util.Log
import com.example.testapplication.data.mapper.toEntity
import com.example.testapplication.data.network.api.ApiService
import com.example.testapplication.domain.entity.Quote
import com.example.testapplication.domain.repository.ListQuoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ListQuoteRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ListQuoteRepository {

    override suspend fun getQuotes(offset: Int): List<Quote> {
        val result = apiService.loadQuotes()
        Log.d("ListQuoteRepositoryImpl", result.toString())
        return result.map { it.toEntity() }
    }

}