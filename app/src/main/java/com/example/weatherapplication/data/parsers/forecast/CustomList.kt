package com.example.weatherapplication.data.parsers.forecast

import com.example.weatherapplication.data.parsers.weather.CloudsDto
import com.example.weatherapplication.data.parsers.weather.MainDto
import com.example.weatherapplication.data.parsers.weather.RainDto
import com.example.weatherapplication.data.parsers.weather.SysDto
import com.example.weatherapplication.data.parsers.weather.WeatherDto
import com.example.weatherapplication.data.parsers.weather.WindDto
import com.google.gson.annotations.SerializedName

data class CustomList(
    @SerializedName("dt") var dt: Int? = null,
    @SerializedName("main") var main: MainDto? = MainDto(),
    @SerializedName("weather") var weather: ArrayList<WeatherDto>? = arrayListOf(),
    @SerializedName("clouds") var clouds: CloudsDto? = CloudsDto(),
    @SerializedName("wind") var wind: WindDto? = WindDto(),
    @SerializedName("visibility") var visibility: Int? = null,
    @SerializedName("pop") var pop: Double? = null,
    @SerializedName("sys") var sys: SysDto? = SysDto(),
    @SerializedName("dt_txt") var dtTxt: String? = null,
    @SerializedName("rain") var rain: RainDto? = RainDto(),
)
