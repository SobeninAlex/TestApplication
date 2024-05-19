package com.example.domain

import com.example.testapplication.domain.entity.Quote
import com.example.testapplication.domain.repository.ListQuoteRepository
import com.example.testapplication.domain.usecase.GetAllQuotesUseCase
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.mock



class GetAllQuotesUseCaseTest {

    private val repository = mock<ListQuoteRepository>()

    @Test
    fun getAllQuotes() = runBlocking {

        val flowListQuote = flow {
            val list = mutableListOf<Quote>()
            for (i in 1..5) {
                list.add(Quote(id = i, createdBy = i, text = "text $i"))
            }
            emit(list.toList())
        }

        Mockito.`when`(repository.getQuotes()).thenReturn(flowListQuote)

        val getAllQuotesUseCase = GetAllQuotesUseCase(repository)

        val actualResult = getAllQuotesUseCase()

        Mockito.verify(repository, Mockito.times(1)).getQuotes()

        Assertions.assertEquals(flowListQuote, actualResult)

        val expectedList = mutableListOf<Quote>()
        val actualList = mutableListOf<Quote>()

        flowListQuote.collect {
            expectedList.addAll(it)
        }

        actualResult.collect {
            actualList.addAll(it)
        }

        Assertions.assertEquals(expectedList, actualList)

    }



}