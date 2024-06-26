package com.example.testapplication.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.testapplication.R

val Typography = Typography(
    bodyMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.roboto_regular)),
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        color = Color.Black,
    ),
    bodySmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.roboto_regular)),
        fontWeight = FontWeight.Thin,
        fontSize = 16.sp,
        color = Color.Black.copy(alpha = 0.6f),
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.roboto_regular)),
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        color = Color.Black
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.roboto_medium)),
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        color = Color.Black
    ),
    displayMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.roboto_italic)),
        fontWeight = FontWeight.Medium,
        fontSize = 26.sp
    )

)