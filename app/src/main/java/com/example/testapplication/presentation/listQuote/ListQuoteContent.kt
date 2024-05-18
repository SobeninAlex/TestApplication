package com.example.testapplication.presentation.listQuote

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.testapplication.domain.entity.Quote
import com.example.testapplication.presentation.ui.component.Loader
import com.example.testapplication.presentation.ui.component.SomeWrong
import com.example.testapplication.presentation.ui.component.TopBar
import com.example.testapplication.presentation.ui.theme.LeftMessage
import com.example.testapplication.presentation.ui.theme.RightMessage

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
                    },
                    nextBatchLoad = {
                        component.loadNextBatch()
                    },
                    isLoadingMore = state.isLoadingMore
                )
            }

            is ListQuoteStore.State.ListQuoteState.StartLoading -> {
                Loader(
                    paddingValues = paddingValues,
                    alignment = Alignment.Center
                )
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
    nextBatchLoad: () -> Unit,
    isLoadingMore: Boolean,
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {

        itemsIndexed(
            items = listQuote,
            key = { _, item ->
                item.id
            }
        ) { index, quote ->

            when (quote.createdBy) {
                0 -> {
                    QuoteItem(
                        quote = quote,
                        alignment = Alignment.CenterStart,
                        roundedCornerShape = RoundedCornerShape(
                            topStart = 12.dp,
                            topEnd = 12.dp,
                            bottomEnd = 12.dp
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
                            topStart = 12.dp,
                            topEnd = 12.dp,
                            bottomStart = 12.dp
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

            if (index == listQuote.size - 1 && isLoadingMore) {
                Loader(
                    alignment = Alignment.BottomCenter
                )
                nextBatchLoad()
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
                containerColor = when (quote.createdBy) {
                    0 -> {
                        LeftMessage
                    }

                    1 -> {
                        RightMessage
                    }

                    else -> throw RuntimeException("unknown item")
                }
            ),
            shape = roundedCornerShape,
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            )
        ) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = quote.text,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
        }
    }
}

