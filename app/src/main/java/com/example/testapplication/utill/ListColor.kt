package com.example.testapplication.utill

import android.graphics.Color.BLACK
import android.graphics.Color.BLUE
import android.graphics.Color.DKGRAY
import android.graphics.Color.GRAY
import android.graphics.Color.GREEN
import android.graphics.Color.MAGENTA
import android.graphics.Color.RED
import android.graphics.Color.WHITE
import android.graphics.Color.LTGRAY
import android.graphics.Color.YELLOW
import android.graphics.Color.CYAN
import androidx.compose.ui.graphics.Color

object ListColor {
    val colors = mapOf(
        "BLACK" to Color(BLACK),
        "DKGRAY" to Color(DKGRAY),
        "GRAY" to Color(GRAY),
        "LTGRAY" to Color(LTGRAY),
        "WHITE" to Color(WHITE),
        "RED" to Color(RED),
        "GREEN" to Color(GREEN),
        "BLUE" to Color(BLUE),
        "YELLOW" to Color(YELLOW),
        "CYAN" to Color(CYAN),
        "MAGENTA" to Color(MAGENTA),
    )

    fun getAlternativeColor(color: Color): Color {
        return when (color) {
            Color(WHITE), Color(GREEN), Color(CYAN), Color(YELLOW) -> Color(DKGRAY)
            else -> Color(WHITE)
        }
    }
}