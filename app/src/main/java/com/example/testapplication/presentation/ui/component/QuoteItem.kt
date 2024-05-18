package com.example.testapplication.presentation.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.testapplication.domain.entity.Quote
import com.example.testapplication.presentation.ui.theme.LeftMessage
import com.example.testapplication.presentation.ui.theme.RightMessage

@Composable
fun QuoteItem(
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