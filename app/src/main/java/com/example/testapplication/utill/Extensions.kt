package com.example.testapplication.utill

import android.graphics.Color.BLACK
import android.graphics.Color.BLUE
import android.graphics.Color.CYAN
import android.graphics.Color.DKGRAY
import android.graphics.Color.GRAY
import android.graphics.Color.GREEN
import android.graphics.Color.LTGRAY
import android.graphics.Color.MAGENTA
import android.graphics.Color.RED
import android.graphics.Color.WHITE
import android.graphics.Color.YELLOW
import androidx.compose.ui.graphics.Color
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnDestroy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

fun ComponentContext.componentScope() =
    CoroutineScope(Dispatchers.Main.immediate + SupervisorJob()).apply {
        lifecycle.doOnDestroy {
            cancel()
        }
    }