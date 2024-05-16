package com.example.testapplication.domain.usecase

import com.example.testapplication.domain.repository.ListQuoteRepository
import javax.inject.Inject

class GetAllQuotesUseCase @Inject constructor(
    private val repository: ListQuoteRepository
) {

    operator fun invoke(offset: Int = 1) = repository.getQuotes(offset = offset)

}