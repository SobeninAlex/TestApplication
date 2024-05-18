package com.example.testapplication.presentation.root

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.example.testapplication.presentation.listQuote.ListQuoteContent
import com.example.testapplication.presentation.ui.theme.TestApplicationTheme

@Composable
fun RootContent(
    component: RootComponent
) {

    TestApplicationTheme {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Children(
                stack = component.stack
            ) {
                when (val instance = it.instance) {
                    is RootComponent.Child.ListQuote -> {
                        ListQuoteContent(component = instance.component)
                    }
                }
            }
        }
    }

}