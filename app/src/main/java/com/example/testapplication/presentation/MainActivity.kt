package com.example.testapplication.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.arkivanov.decompose.defaultComponentContext
import com.example.testapplication.CustomTestApp
import com.example.testapplication.presentation.root.DefaultRootComponent
import com.example.testapplication.presentation.root.RootContent
import com.example.testapplication.presentation.ui.component.SplashScreen
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var rootComponentFactory: DefaultRootComponent.Factory

    private val appComponent by lazy {
        (applicationContext as CustomTestApp).applicationComponent
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)

        super.onCreate(savedInstanceState)

        installSplashScreen()

        val root = rootComponentFactory.create(defaultComponentContext())

        setContent {

            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = "splash_screen"
            ) {
                composable("splash_screen") {
                    SplashScreen(navHostController = navController)
                }

                composable("root_screen") {
                    RootContent(component = root)
                }
            }
        }
    }

}