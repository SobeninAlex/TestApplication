package com.example.testapplication.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.defaultComponentContext
import com.example.testapplication.CustomTextApp
import com.example.testapplication.presentation.root.DefaultRootComponent
import com.example.testapplication.presentation.root.RootContent
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var rootComponentFactory: DefaultRootComponent.Factory

    private val appComponent by lazy {
        (applicationContext as CustomTextApp).applicationComponent
    }

    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)

        super.onCreate(savedInstanceState)

        val root = rootComponentFactory.create(defaultComponentContext())

        setContent {
            RootContent(component = root)
        }
    }

}