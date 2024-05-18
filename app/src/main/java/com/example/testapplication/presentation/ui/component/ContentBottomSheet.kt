package com.example.testapplication.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.testapplication.domain.entity.Quote
import com.example.testapplication.utill.ListColor

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ContentBottomSheet(
    modifier: Modifier = Modifier,
    quote: Quote,
    tags: List<String>,
    colors: List<String>
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = quote.text,
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    text = quote.createdAt,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }


            Spacer(modifier = modifier.height(16.dp))
            HorizontalDivider(thickness = 2.dp, color = DividerDefaults.color)
            Text(
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(
                        start = 16.dp, end = 16.dp, top = 16.dp
                    ),
                text = "tags",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground
            )

            FlowRow(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.Start),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                tags.forEach { tag ->

                    val cardColor = if (colors.isEmpty()) {
                        Color(android.graphics.Color.GRAY)
                    } else {
                        val color = colors.random()
                        ListColor.colors[color] ?: Color(android.graphics.Color.GRAY)
                    }

                    val textColor = ListColor.getAlternativeColor(cardColor)

                    Card(
                        modifier = Modifier
                            .wrapContentSize(),
                        shape = RoundedCornerShape(6.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = cardColor
                        )
                    ) {
                        Text(
                            modifier = Modifier.padding(6.dp),
                            text = tag,
                            style = MaterialTheme.typography.bodyMedium,
                            color = textColor,
                        )
                    }
                }
            }
        }
    }
}