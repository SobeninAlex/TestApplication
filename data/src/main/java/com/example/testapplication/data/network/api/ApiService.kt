package com.example.testapplication.data.network.api

import com.example.testapplication.data.network.dto.QuoteDetailDto
import com.example.testapplication.data.network.dto.QuoteDto
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("test")
    suspend fun loadQuotes(
        @Query("limit") limit: Int = 10,
        @Query("offset") offset: Int = 1
    ): List<QuoteDto>

    @GET("test/{id}")
    suspend fun loadQuoteDetail(
        @Path("id") id: Int
    ): QuoteDetailDto

}