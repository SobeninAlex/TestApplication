package com.example.testapplication.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.arkivanov.decompose.defaultComponentContext
import com.example.testapplication.CustomTextApp
import com.example.testapplication.presentation.root.DefaultRootComponent
import com.example.testapplication.presentation.root.RootContent
import com.example.testapplication.presentation.ui.theme.TestApplicationTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var rootComponentFactory: DefaultRootComponent.Factory

    private val appComponent by lazy {
        (applicationContext as CustomTextApp).applicationComponent
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)

        super.onCreate(savedInstanceState)

        val root = rootComponentFactory.create(defaultComponentContext())

        setContent {
            RootContent(component = root)
        }
    }

}