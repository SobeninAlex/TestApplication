package com.example.testapplication.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.testapplication.domain.entity.Quote

@Composable
fun QuoteList(
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