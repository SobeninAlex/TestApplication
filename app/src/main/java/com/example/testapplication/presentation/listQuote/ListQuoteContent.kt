package com.example.testapplication.presentation.listQuote

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.testapplication.presentation.ui.component.ContentBottomSheet
import com.example.testapplication.presentation.ui.component.Loader
import com.example.testapplication.presentation.ui.component.QuoteList
import com.example.testapplication.presentation.ui.component.SomeWrong
import com.example.testapplication.presentation.ui.component.TopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListQuoteContent(
    component: ListQuoteComponent,
) {

    val state by component.model.collectAsState()

    val bottomSheetState = rememberModalBottomSheetState()
    var shadowBottomSheet by remember {
        mutableStateOf(false)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopBar()
        },
    ) { paddingValues ->

        when (val current = state.listQuoteState) {
            is ListQuoteStore.State.ListQuoteState.Initial -> {
            }

            is ListQuoteStore.State.ListQuoteState.LoadingError -> {
                SomeWrong(paddingValues = paddingValues)
            }

            is ListQuoteStore.State.ListQuoteState.LoadingSuccess -> {
                QuoteList(
                    listQuote = current.quotes,
                    paddingValues = paddingValues,
                    onClickItem = {
                        component.onClickQuoteItem(it)
                        shadowBottomSheet = true
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

    if (shadowBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { shadowBottomSheet = false },
            sheetState = bottomSheetState,
            containerColor = MaterialTheme.colorScheme.background,
            shape = RoundedCornerShape(topStart = 42.dp, topEnd = 42.dp)
        ) {
            state.quoteDetail?.let { quote ->
                ContentBottomSheet(
                    quote = quote,
                    tags = quote.tags,
                    colors = quote.colors
                )
            }
        }
    }
}





