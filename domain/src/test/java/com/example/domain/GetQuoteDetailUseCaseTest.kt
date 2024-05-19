package com.example.domain

import com.example.testapplication.domain.entity.Quote
import com.example.testapplication.domain.repository.DetailQuoteRepository
import com.example.testapplication.domain.repository.ListQuoteRepository
import com.example.testapplication.domain.usecase.GetAllQuotesUseCase
import com.example.testapplication.domain.usecase.GetQuoteDetailUseCase
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.mock


class GetQuoteDetailUseCaseTest {

    private val repository = mock<DetailQuoteRepository>()

    @Test
    fun getAllQuotes() = runBlocking {

        val quote = Quote(
            id = 1,
            createdBy = 1,
            text = "text"
        )

        Mockito.`when`(repository.getQuote(1)).thenReturn(quote)

        val getQuoteDetailUseCase = GetQuoteDetailUseCase(repository)

        val actualResult = getQuoteDetailUseCase(1)

        Mockito.verify(repository, Mockito.times(1)).getQuote(1)

        Assertions.assertEquals(quote, actualResult)
    }

}