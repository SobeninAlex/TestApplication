package com.example.testapplication

import android.app.Application
import com.example.testapplication.di.ApplicationComponent
import com.example.testapplication.di.DaggerApplicationComponent

class CustomTextApp : Application() {

    val applicationComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.create()
    }

}