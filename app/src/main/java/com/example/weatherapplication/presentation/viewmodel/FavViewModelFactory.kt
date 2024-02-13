package com.example.weatherapplication.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapplication.data.room.FavViewModel

class FavViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavViewModel::class.java)) {
            return FavViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}