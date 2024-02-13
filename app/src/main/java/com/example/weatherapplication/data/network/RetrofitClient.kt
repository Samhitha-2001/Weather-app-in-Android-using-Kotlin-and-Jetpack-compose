package com.example.weatherapplication.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    companion object{
        private var apiService: WeatherForecastApi? = null
        fun getApiInstance(): WeatherForecastApi{
            if (apiService == null ) {
                apiService = Retrofit.Builder()
                    .baseUrl("http://api.openweathermap.org/data/2.5/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(WeatherForecastApi::class.java)
            }
            return  apiService!!
        }
    }
}