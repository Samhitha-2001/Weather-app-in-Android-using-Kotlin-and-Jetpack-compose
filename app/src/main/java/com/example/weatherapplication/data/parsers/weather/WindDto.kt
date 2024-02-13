package com.example.weatherapplication.data.parsers.weather

import com.google.gson.annotations.SerializedName

data class WindDto(
    @SerializedName("speed") var speed: Double? = null,
    @SerializedName("deg") var deg: Int? = null
)
