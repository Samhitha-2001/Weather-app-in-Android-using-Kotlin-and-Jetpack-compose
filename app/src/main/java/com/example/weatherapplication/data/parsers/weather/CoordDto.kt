package com.example.weatherapplication.data.parsers.weather

import com.google.gson.annotations.SerializedName

data class CoordDto(
    @SerializedName("lon") var long: Double? = null,
    @SerializedName("lat") var lat: Double? = null
)
