package com.example.testapplication.presentation.listQuote

import kotlinx.coroutines.flow.StateFlow

interface ListQuoteComponent {

    //модель экрана
    val model: StateFlow<ListQuoteStore.State>

    //действия пользователя
    fun onClickQuoteItem(quoteId: Int)

    fun loadNextBatch()

}