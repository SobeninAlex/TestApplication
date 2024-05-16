package com.example.testapplication.presentation.detailQuote

import kotlinx.coroutines.flow.StateFlow

interface DetailQuoteComponent {

    val model: StateFlow<DetailQuoteStore.State>

    fun onClickBack()

}