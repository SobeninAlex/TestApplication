package com.example.testapplication.presentation.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun Loader(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues()
) {
    Box(modifier = modifier
        .fillMaxSize()
        .padding(paddingValues))
    {
        CircularProgressIndicator(
            modifier = modifier
                .align(Alignment.Center),
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}