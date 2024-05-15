package com.example.testapplication.di

import com.example.testapplication.di.annotation.ApplicationScope
import com.example.testapplication.di.module.DataModule
import com.example.testapplication.di.module.PresentationModule
import com.example.testapplication.presentation.MainActivity
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        PresentationModule::class,
    ]
)
interface ApplicationComponent {

    fun inject(mainActivity: MainActivity)

}