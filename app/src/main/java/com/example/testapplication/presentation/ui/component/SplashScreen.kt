package com.example.testapplication.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.testapplication.R
import com.example.testapplication.presentation.ui.theme.Primary
import kotlinx.coroutines.delay

@Composable
fun SplashScreen (
    navHostController: NavHostController
) {

    LaunchedEffect(key1 = true) {
        delay(1700)
        navHostController.popBackStack()
        navHostController.navigate("root_screen")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Primary),
        contentAlignment = Alignment.Center
    ) {
        LoaderAnimation(
            modifier = Modifier.size(400.dp),
            anim = R.raw.anim_1
        )
    }

}

@Composable
fun LoaderAnimation(
    modifier: Modifier,
    anim: Int
) {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(anim))

    LottieAnimation(
        composition = composition,
        modifier = modifier
    )
}