package com.example.weatherapplication.data.parsers.weather

import com.google.gson.annotations.SerializedName

data class WeatherResult(
    @SerializedName("coord") var coord: CoordDto? = CoordDto(),
    @SerializedName("weather") var weather: ArrayList<WeatherDto>? = arrayListOf(),
    @SerializedName("base") var base: String? = null,
    @SerializedName("main") var main: MainDto? = MainDto(),
    @SerializedName("visibility") var visibility: Int? = null,
    @SerializedName("wind") var wind: WindDto? = WindDto(),
    @SerializedName("clouds") var clouds: CloudsDto? = CloudsDto(),
    @SerializedName("rain") var rain: RainDto? = RainDto(),
    @SerializedName("dt") var dt: Int? = null,
    @SerializedName("sys") var sys: SysDto? = SysDto(),
    @SerializedName("timezone") var timezone: Int? = null,
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("cod") var cod: Int? = null,
    @SerializedName("snow") var snow: SnowDto? = SnowDto(),

)
