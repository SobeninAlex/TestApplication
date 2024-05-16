package com.example.testapplication.presentation.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.example.testapplication.presentation.detailQuote.DetailQuoteComponent
import com.example.testapplication.presentation.listQuote.ListQuoteComponent

interface RootComponent {

    val stack: Value<ChildStack<*, Child>>

    sealed interface Child {

        data class ListQuote(val component: ListQuoteComponent) : Child

        data class DetailQuote(val component: DetailQuoteComponent) : Child

    }

}