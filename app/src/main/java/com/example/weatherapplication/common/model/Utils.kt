package com.example.weatherapplication.common.model

import com.example.weatherapplication.common.constants.Constant.Companion.apiKey
import java.text.SimpleDateFormat

class Utils {
    companion object {
        fun timeStampToHumanDate(timeStamp: Long, format: String): String {
            val dateFormat = SimpleDateFormat(format)
            return dateFormat.format(timeStamp*1000)
        }

        fun buildIcon(icon: String, iSBigSize: Boolean = true): String {
            return if (iSBigSize) {
                "https://openweathermap.org/img/wn/${icon}@4x.png"
            }
            else {
                "https://openweathermap.org/img/wn/${icon}.png"
            }
        }

//        fun getWeatherInfoByCityName(city: String): String {
//            return "https://api.openweathermap.org/data/2.5/weather?q=${city}&appid=${apiKey}"
//        }
    }
}