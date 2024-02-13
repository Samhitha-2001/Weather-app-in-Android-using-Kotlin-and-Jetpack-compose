package com.example.weatherapplication.data.parsers.forecast

import com.example.weatherapplication.data.parsers.weather.RainDto
import com.google.gson.annotations.SerializedName

data class ForecastResult(
    @SerializedName("cod") var cod: String? = null,
    @SerializedName("message") var message: Int? = null,
    @SerializedName("cnt") var cnt: Int? = null,
    @SerializedName("list") var list: ArrayList<CustomList>? = arrayListOf(),
    @SerializedName("city") var city: CityDto? = CityDto(),

    )
