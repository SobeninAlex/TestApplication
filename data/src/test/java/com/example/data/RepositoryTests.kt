package com.example.data

import com.example.testapplication.data.mapper.toEntity
import com.example.testapplication.data.mapper.toListEntity
import com.example.testapplication.data.network.api.ApiService
import com.example.testapplication.data.network.dto.QuoteDetailDto
import com.example.testapplication.data.network.dto.QuoteDto
import com.example.testapplication.data.repository.DetailQuoteRepositoryImpl
import com.example.testapplication.data.repository.ListQuoteRepositoryImpl
import com.example.testapplication.domain.entity.Quote
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito
import org.mockito.Mockito.mock


class RepositoryTests {

    private val apiService = mock<ApiService>()

    @Test
    fun getQuotes() = runBlocking {
        val listQuoteDto = buildList {
            for (i in 1..5) {
                add(QuoteDto(id = i, createdBy = i, text = "text $i"))
            }
        }

        Mockito.`when`(apiService.loadQuotes()).thenReturn(listQuoteDto)

        val repository = ListQuoteRepositoryImpl(apiService)
        var result: List<Quote>? = null
        repository.getQuotes().collect {
            result = it
        }

        Mockito.verify(apiService, Mockito.times(1)).loadQuotes()

        Assertions.assertEquals(listQuoteDto.toListEntity(), result)
    }

    @Test
    fun getQuote() = runBlocking {
        val expectedQuote = QuoteDetailDto(
            id = 1,
            createdBy = 1,
            text = "text 1",
            createdAt = "1",
            tagList = emptyList(),
            colors = emptyList()
        )

        Mockito.`when`(apiService.loadQuoteDetail(1)).thenReturn(expectedQuote)

        val repository = DetailQuoteRepositoryImpl(apiService)
        val result = repository.getQuote(1)

        Mockito.verify(apiService, Mockito.times(1)).loadQuoteDetail(1)

        Assertions.assertEquals(expectedQuote.toEntity(), result)
    }

}