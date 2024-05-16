package com.example.testapplication.presentation.listQuote

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.testapplication.domain.entity.Quote
import com.example.testapplication.presentation.ui.component.Loader
import com.example.testapplication.presentation.ui.component.SomeWrong
import com.example.testapplication.presentation.ui.component.TopBar

@Composable
fun ListQuoteContent(
    component: ListQuoteComponent,
) {

    val state by component.model.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopBar()
        }
    ) { paddingValues ->

        when (val current = state.listQuoteState) {
            is ListQuoteStore.State.ListQuoteState.Initial -> {

            }

            is ListQuoteStore.State.ListQuoteState.LoadingError -> {
                SomeWrong(paddingValues = paddingValues)
            }

            is ListQuoteStore.State.ListQuoteState.LoadingSuccess -> {
                Content(
                    listQuote = current.quotes,
                    paddingValues = paddingValues,
                    onClickItem = {
                        component.onClickQuoteItem(it)
                    }
                )
            }

            is ListQuoteStore.State.ListQuoteState.StartLoading -> {
                Loader(paddingValues = paddingValues)
            }
        }

    }

}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    listQuote: List<Quote>,
    paddingValues: PaddingValues = PaddingValues(),
    onClickItem: (Int) -> Unit,
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = paddingValues,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(
            items = listQuote,
            key = { it.id }
        ) { quote ->
            QuoteItem(
                quote = quote,
                onClickItem = {
                    onClickItem(quote.id)
                }
            )
        }
    }

}

@Composable
private fun QuoteItem(
    quote: Quote,
    onClickItem: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxSize().clickable { onClickItem() },
        colors = CardDefaults.cardColors(
            //TODO
        ),
        shape = RoundedCornerShape(size = 8.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = quote.text)
        }
    }
}

