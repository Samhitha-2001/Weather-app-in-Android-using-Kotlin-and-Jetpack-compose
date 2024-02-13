package com.example.weatherapplication.common.constants

class Constant {
    companion object {
        val permissions = arrayOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
        const val apiKey = "c4dfa93b8b2c9ddb2531f7de56137c0e"

        const val LOADING = "Loading..."
        const val NA = "No Info"
    }
}