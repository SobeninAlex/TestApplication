package com.example.testapplication.domain.usecase

import com.example.testapplication.domain.repository.DetailQuoteRepository
import javax.inject.Inject

class GetQuoteDetailUseCase @Inject constructor(
    private val repository: DetailQuoteRepository
) {

    suspend operator fun invoke(quoteId: Int) =
        repository.getQuote(quoteId = quoteId)

}