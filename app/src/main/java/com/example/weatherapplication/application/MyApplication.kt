package com.example.weatherapplication.application

import android.app.Application
//import androidx.multidex.MultiDexApplication
import com.example.weatherapplication.presentation.viewmodel.ApplicationContextProvider

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        ApplicationContextProvider.initialize(this)
    }
}