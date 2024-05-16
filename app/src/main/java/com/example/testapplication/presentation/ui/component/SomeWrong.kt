package com.example.testapplication.presentation.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.example.testapplication.R

@Composable
fun SomeWrong(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues()
) {
    Box(modifier = modifier
        .fillMaxSize()
        .padding(paddingValues)
    ) {
        Text(
            modifier = modifier
                .align(Alignment.Center),
            text = stringResource(R.string.some_wrong),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}