package com.example.weatherapplication.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weatherapplication.R
import com.example.weatherapplication.common.constants.Constant
import com.example.weatherapplication.common.constants.Constant.Companion.NA
import com.example.weatherapplication.data.parsers.forecast.ForecastResult
import com.example.weatherapplication.common.model.Utils.Companion.buildIcon
import com.example.weatherapplication.common.model.Utils.Companion.timeStampToHumanDate
import com.example.weatherapplication.presentation.ui.theme.DarkBlue
import com.example.weatherapplication.presentation.ui.theme.DeepBlue

@Composable
fun ForecastScreen(forecastResponse: ForecastResult) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        forecastResponse.list!!.let {
            listForecast ->
            if (listForecast.size > 0) {
                LazyRow(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(listForecast!!) {
                            item ->
                            item.let { _ ->
                                var temp = ""
                                var icon = ""
                                var time = ""
                                var wind = ""
                                var cloud = ""
//                                var snow = ""
                                var humidity = ""
                                var pressure = ""
                                var rain = ""

                                item.main.let { main ->
                                    temp = if (main == null) NA else "${main.temp} °C"
                                }
                                item.weather.let { weather ->
                                    icon = if (weather == null) NA else buildIcon(weather[0].icon!!, iSBigSize = false)
                                }
                                item.dt.let { dateTime ->
                                    time = if (dateTime == null) NA else timeStampToHumanDate(dateTime.toLong(), "EEE: hh:mm aa")
                                }
                                item.wind.let {
                                    wind = if (it == null) Constant.LOADING else "${it.speed}"
                                }
                                item.clouds.let {
                                    cloud = if (it == null) Constant.LOADING else "${it.all}"
                                }
                                item.main?.let {
                                    humidity = "${it.humidity}%"
                                }
                                item.main?.let {
                                    pressure = "${it.pressure}\nhpa"
                                }
                                item.rain.let {
                                    rain = if (it!!.d1h == null) NA else "${it.d1h}"
                                }

                                ForecastRow(
                                    temp = temp,
                                    image = icon,
                                    time = time,
                                    wind = wind,
                                    cloud = cloud,
                                    humidity = humidity,
                                    pressure = pressure,
                                    rain = rain
                                )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ForecastRow(
    temp: String,
    image: String,
    time: String,
    wind: String,
    cloud: String,
    humidity: String,
    pressure: String,
    rain: String

    ) {
    var showMoreForecast by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = DeepBlue,
            contentColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .padding(80.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = temp.ifEmpty { NA },
                color = Color.White,
                fontSize = 24.sp
            )
            AsyncImage(
                model = image,
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp),
                contentScale = ContentScale.FillBounds
            )
            Text(
                text = time.ifEmpty { NA },
                color =  Color.White,
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.padding(16.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (!showMoreForecast) {
                    ShowButton(
                        text = stringResource(R.string.show_more),
                        icon = Icons.Default.ArrowDropDown,
                        color = DarkBlue
                    ) {
                        showMoreForecast = true
                    }
                }
                else {
                    ShowButton(
                        text = stringResource(R.string.show_less),
                        icon = Icons.Filled.KeyboardArrowUp,
                        color = DarkBlue
                    ) {
                        showMoreForecast = false
                    }
                }
            }
            if (showMoreForecast) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        WeatherInfo(icon = painterResource(R.drawable.wind), text = "$wind\nm/s")
                        Spacer(modifier = Modifier.padding(20.dp))
                        WeatherInfo(icon = painterResource(R.drawable.cloud), text = "$cloud%")
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        WeatherInfo(icon = painterResource(R.drawable.droplet), text = "$humidity")
                        Spacer(modifier = Modifier.padding(20.dp))
                        WeatherInfo(icon = painterResource(R.drawable.pressure), text = "$pressure")
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        WeatherInfo(
                            icon = painterResource(R.drawable.cloud_rain),
                            text = if(rain == NA) rain else "$rain mm"
                        )
                    }
                }
            }
        }
    }
}
