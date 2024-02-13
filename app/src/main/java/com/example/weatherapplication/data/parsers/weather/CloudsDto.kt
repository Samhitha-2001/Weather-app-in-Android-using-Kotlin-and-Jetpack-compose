package com.example.weatherapplication.data.parsers.weather

import com.google.gson.annotations.SerializedName

data class CloudsDto(
    @SerializedName("all") var all: Int? = null
)
