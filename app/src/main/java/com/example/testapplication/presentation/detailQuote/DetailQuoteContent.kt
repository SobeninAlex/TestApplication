package com.example.testapplication.presentation.detailQuote

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun DetailQuoteContent(
    component: DetailQuoteComponent,
) {

    val state by component.model.collectAsState()

}