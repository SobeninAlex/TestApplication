package com.example.testapplication.presentation.listQuote

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun ListQuoteContent(
    component: ListQuoteComponent,
) {

    val state by component.model.collectAsState()

}