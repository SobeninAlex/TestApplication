package com.example.testapplication.presentation.detailQuote

import android.graphics.Color.GRAY
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.testapplication.domain.entity.Quote
import com.example.testapplication.presentation.ui.component.Loader
import com.example.testapplication.presentation.ui.component.SomeWrong
import com.example.testapplication.utill.ListColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailQuoteContent(
    component: DetailQuoteComponent,
) {

    val state by component.model.collectAsState()

    val bottomSheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()

    ModalBottomSheet(
        sheetState = bottomSheetState,
        tonalElevation = 5.dp,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        containerColor = MaterialTheme.colorScheme.background,
        onDismissRequest = {
            component.onClickBack()
        },
    ) {
        when (val current = state.detailQuoteState) {
            is DetailQuoteStore.State.DetailQuoteState.Initial -> {
            }

            is DetailQuoteStore.State.DetailQuoteState.LoadingError -> {
                SomeWrong()
            }

            is DetailQuoteStore.State.DetailQuoteState.LoadingSuccess -> {
                Content(
                    quote = current.quote,
                    tags = current.quote.tags,
                    colors = current.quote.colors
                )
            }

            is DetailQuoteStore.State.DetailQuoteState.StartLoading -> {
                Loader(alignment = Alignment.Center)
            }
        }
    }


}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun Content(
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
                        Color(GRAY)
                    } else {
                        val color = colors.random()
                        ListColor.colors[color] ?: Color(GRAY)
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















