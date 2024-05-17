package com.example.testapplication.presentation.detailQuote

import android.graphics.Color.GRAY
import android.graphics.Color.TRANSPARENT
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.testapplication.R
import com.example.testapplication.domain.entity.Quote
import com.example.testapplication.presentation.ui.component.Loader
import com.example.testapplication.presentation.ui.component.SomeWrong
import com.example.testapplication.presentation.ui.theme.Primary
import com.example.testapplication.utill.ListColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailQuoteContent(
    component: DetailQuoteComponent,
) {

    val state by component.model.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.quote_with_id, state.quoteId),
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Primary
                ),
                navigationIcon = {
                    IconButton(
                        onClick = { component.onClickBack() },
                    ) {
                        Icon(
                            Icons.Default.ArrowBackIosNew,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        when (val current = state.detailQuoteState) {
            is DetailQuoteStore.State.DetailQuoteState.Initial -> {
            }

            is DetailQuoteStore.State.DetailQuoteState.LoadingError -> {
                SomeWrong(paddingValues = paddingValues)
            }

            is DetailQuoteStore.State.DetailQuoteState.LoadingSuccess -> {
                Content(
                    quote = current.quote,
                    paddingValues = paddingValues,
                    tags = current.quote.tags,
                    colors = current.quote.colors
                )
            }

            is DetailQuoteStore.State.DetailQuoteState.StartLoading -> {
                Loader(alignment = Alignment.Center, paddingValues = paddingValues)
            }
        }

    }

}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    quote: Quote,
    paddingValues: PaddingValues,
    tags: List<String>,
    colors: List<String>
) {

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier.padding(paddingValues),
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

            LazyRow(
                modifier = Modifier.padding(all = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {

                items(
                    items = tags
                ) { item ->
                    Card(
                        modifier = Modifier
                            .wrapContentSize(),
                        shape = RoundedCornerShape(6.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (colors.isEmpty()) {
                                Color(GRAY)
                            } else {
                                val color = colors.random()
                                ListColor.colors[color] ?: Color(TRANSPARENT)
                            }
                        )
                    ) {
                        Text(
                            modifier = Modifier.padding(6.dp),
                            text = item,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                    }
                }
            }
        }
    }


}















