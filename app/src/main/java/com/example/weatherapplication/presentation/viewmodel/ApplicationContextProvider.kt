package com.example.weatherapplication.presentation.viewmodel

import android.app.Application

object ApplicationContextProvider {

    private lateinit var application: Application

    fun initialize(app: Application) {
        application = app
    }

    fun getApplicationContext(): Application{
        return  application
    }
}