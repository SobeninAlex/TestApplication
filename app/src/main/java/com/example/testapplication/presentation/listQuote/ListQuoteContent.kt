package com.example.testapplication.presentation.listQuote

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.Alignment
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
            .fillMaxSize()
            .padding(paddingValues),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(
            items = listQuote,
            key = { it.id }
        ) { quote ->
            when (quote.createdBy) {
                0 -> {
                    QuoteItem(
                        quote = quote,
                        alignment = Alignment.CenterStart,
                        roundedCornerShape = RoundedCornerShape(
                            topStart = 8.dp,
                            topEnd = 8.dp,
                            bottomEnd = 8.dp
                        ),
                        onClickItem = {
                            onClickItem(quote.id)
                        }
                    )
                }

                1 -> {
                    QuoteItem(
                        quote = quote,
                        alignment = Alignment.CenterEnd,
                        roundedCornerShape = RoundedCornerShape(
                            topStart = 8.dp,
                            topEnd = 8.dp,
                            bottomStart = 8.dp
                        ),
                        onClickItem = {
                            onClickItem(quote.id)
                        }
                    )
                }

                else -> {
                    throw RuntimeException("unknown item")
                }
            }
        }
    }

}

@Composable
private fun QuoteItem(
    quote: Quote,
    alignment: Alignment,
    roundedCornerShape: RoundedCornerShape,
    onClickItem: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Card(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(0.6f)
                .align(alignment)
                .clickable { onClickItem() },
            colors = CardDefaults.cardColors(
                //TODO
            ),
            shape = roundedCornerShape,
            border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.onBackground),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            )
        ) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = quote.text
            )
        }
    }
}

