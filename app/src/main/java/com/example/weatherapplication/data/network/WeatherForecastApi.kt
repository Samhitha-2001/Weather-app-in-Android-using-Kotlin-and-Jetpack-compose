package com.example.weatherapplication.data.network

import com.example.weatherapplication.common.constants.Constant
import com.example.weatherapplication.common.constants.Constant.Companion.apiKey
import com.example.weatherapplication.data.parsers.forecast.ForecastResult
import com.example.weatherapplication.data.parsers.weather.WeatherResult
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherForecastApi {

    @GET("weather")
    suspend fun getWeather(
        @Query("lat") lat: Double = 0.0,
        @Query("lon") long: Double = 0.0,
        @Query("units") units: String = "metric",
        @Query("appid") appId: String = apiKey
    ): WeatherResult

    @GET("forecast")
    suspend fun getForecast(
        @Query("lat") lat: Double = 0.0,
        @Query("lon") long: Double = 0.0,
        @Query("units") units: String = "metric",
        @Query("appid") appId: String = apiKey
    ): ForecastResult

    @GET("weather")
    suspend fun getWeatherByCity(
        @Query("q") city: String,
        @Query("units") units: String = "metric",
        @Query("appid") appId: String = apiKey
    ): WeatherResult

    @GET("forecast")
    suspend fun getForeCastByCity(
        @Query("q") city: String,
        @Query("units") units: String = "metric",
        @Query("appid") appId: String = apiKey
    ): ForecastResult
}