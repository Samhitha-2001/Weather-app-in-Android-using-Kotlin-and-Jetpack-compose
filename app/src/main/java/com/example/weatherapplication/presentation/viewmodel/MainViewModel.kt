package com.example.weatherapplication.presentation.viewmodel

import android.app.Application
import androidx.compose.material.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapplication.data.network.RetrofitClient
import com.example.weatherapplication.data.parsers.forecast.ForecastResult
import com.example.weatherapplication.data.parsers.weather.WeatherResult
import com.example.weatherapplication.common.model.LatLong
import com.example.weatherapplication.data.room.FavViewModel
import com.example.weatherapplication.presentation.screens.alertScreen
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay

class MainViewModel: ViewModel() {

    var state by mutableStateOf(State.LOADING)
    var weatherResponse: WeatherResult by mutableStateOf(WeatherResult())
    var forecastResponse: ForecastResult by mutableStateOf(ForecastResult())
    var errorMessage: String by mutableStateOf("")

    var location by  mutableStateOf("")
    var locInput by mutableStateOf(false)
    var isSearchIconVisible by mutableStateOf(true)
    var isTextFieldVisible by mutableStateOf(false)

    var reEnterCityName by mutableStateOf(false)
    var showAlert by mutableStateOf(false)
//    var favViewModel: FavViewModel = FavViewModel(application = Application())
//    val _favList = mutableSetOf<String>()
    private val application: Application = ApplicationContextProvider.getApplicationContext()

    var favViewModel: FavViewModel = FavViewModel(application)



    var alertReturn by mutableStateOf("")
    var showFavScreen by mutableStateOf(false)
    var showErrorAlert by mutableStateOf(false)


    fun getWeatherByLocation(latLong: LatLong) {
        viewModelScope.launch {
            state = State.LOADING
            val apiService = RetrofitClient.getApiInstance()


            try {
                if (locInput){
                    if (location == "") {
                        kotlinx.coroutines.delay(1500)
                        val apiResponse = apiService.getWeather(latLong.lat, latLong.long)
                        weatherResponse = apiResponse
                        var name  = ""
                        weatherResponse.name?.let {
                            name = it
                        }
                        state = State.SUCCESS
                        showAlert = !favViewModel.isCityInFavourites(name) && alertReturn == ""
//                        isSearchIconVisible = false
                    }
                    else {
                        kotlinx.coroutines.delay(3000)
                        val apiResponse = apiService.getWeatherByCity(location)
                        weatherResponse = apiResponse
                        state = State.SUCCESS
//                        if (!_favList.contains(location)) {
//                            showAlert = true
//                        }
                        showAlert = !favViewModel.isCityInFavourites(location) && alertReturn == ""
                    }
                }
                else {
                    val apiResponse = apiService.getWeather(latLong.lat, latLong.long)
                    weatherResponse = apiResponse
                    var name  = ""
                    weatherResponse?.name?.let {
                        name = it
                    }
                    state = State.SUCCESS
//                    if (!_favList.contains(name)) {
//                        showAlert = true
//                    }
                    showAlert = !favViewModel.isCityInFavourites(name) && alertReturn == ""
                }
            }
            catch (e: Exception) {
                if (e.message!!.toString() == "HTTP 404 Not Found") {
                    errorMessage = "Enter a valid city name"
//                    reEnterCityName = true
//                    state = State.FAILED
                    showErrorAlert = true
//                    location = ""
                    kotlinx.coroutines.delay(2000)
                    state = State.SUCCESS
                    isTextFieldVisible = true
                }
                else if (e.message!!.toString() == "HTTP 400 Bad Request") {
//                    kotlinx.coroutines.delay(2000)
                    state = State.LOADING
                    errorMessage = ""
                    kotlinx.coroutines.delay(2000)
                    state = State.SUCCESS
                }
                else {
                    errorMessage = e.message!!.toString()
                    state = State.FAILED
                }
            }
        }
    }

    fun getForecastByLocation(latLong: LatLong) {
        viewModelScope.launch {
            state = State.LOADING
            val apiService = RetrofitClient.getApiInstance()

            try {
                if (locInput) {
                    if (location == "" ) {
                        kotlinx.coroutines.delay(1500)
                        val apiResponse = apiService.getForecast(latLong.lat, latLong.long)
                        forecastResponse = apiResponse
                        state = State.SUCCESS
//                        isSearchIconVisible = false
                    }
                    else {
                        kotlinx.coroutines.delay(3000)
                        val apiResponse = apiService.getForeCastByCity(location)
                        forecastResponse = apiResponse
                        state = State.SUCCESS
                    }
                }
                else {
                    val apiResponse = apiService.getForecast(latLong.lat, latLong.long)
                    forecastResponse = apiResponse
                    state = State.SUCCESS
                }
            }
            catch (e: Exception) {
                if (e.message!!.toString() == "HTTP 404 Not Found") {
                    errorMessage = "Enter a valid city name"
//                    reEnterCityName = true
                    state = State.SUCCESS
                    isTextFieldVisible = true

                }
                else if (e.message!!.toString() == "HTTP 400 Bad Request") {
                    kotlinx.coroutines.delay(2000)
                    errorMessage = ""
                    state = State.LOADING
                    kotlinx.coroutines.delay(2000)
                    state = State.SUCCESS
                }
                else {
                    errorMessage = e.message!!.toString()
                    state = State.FAILED
                }
            }
        }
    }
}



