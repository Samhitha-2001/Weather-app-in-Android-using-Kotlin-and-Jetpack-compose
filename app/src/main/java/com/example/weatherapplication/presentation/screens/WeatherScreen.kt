package com.example.weatherapplication.presentation.screens

import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.weatherapplication.R
import com.example.weatherapplication.common.constants.Constant.Companion.LOADING
import com.example.weatherapplication.common.constants.Constant.Companion.NA
import com.example.weatherapplication.data.parsers.weather.WeatherResult
import com.example.weatherapplication.common.model.Utils.Companion.buildIcon
import com.example.weatherapplication.common.model.Utils.Companion.timeStampToHumanDate
import com.example.weatherapplication.presentation.ui.theme.DarkBlue
import com.example.weatherapplication.presentation.ui.theme.DeepBlue
import com.example.weatherapplication.presentation.viewmodel.ApplicationContextProvider
import com.example.weatherapplication.presentation.viewmodel.MainViewModel

var showMore by mutableStateOf(false)
//var showLess by mutableStateOf(false)
var showFavScreen by mutableStateOf(false)
//private val application: Application = ApplicationContextProvider.getApplicationContext()
//private var mainViewModel: MainViewModel = MainViewModel(application)

@Composable
fun WeatherScreen(weatherResponse: WeatherResult) {

    var placeName = ""
    if (!weatherResponse.name.isNullOrEmpty()) {
        weatherResponse?.name?.let {
            placeName = it
        }
    }
    else {
        weatherResponse.coord?.let {
            placeName = "${it.lat}/${it.long}"
        }
    }

    var date = ""
    val dateVal = (weatherResponse.dt?:0)
    date = if (dateVal == 0) LOADING
    else timeStampToHumanDate(dateVal.toLong(), "dd-MM-yyyy")

    var icon = ""
    var description = ""
    weatherResponse.weather.let {
        if (it!!.size>0) {
            description = if (it[0].description == null) LOADING else it[0].description!!
            icon = if (it[0].icon == null) LOADING else it[0].icon!!
        }
    }

    var temp = ""
    weatherResponse.main?.let {
        temp = "${it.temp} °C"
    }

    var feelsLike = ""
    weatherResponse.main?.let {
        feelsLike = "${it.feelsLike} °C"
    }

    var currTime = ""
    weatherResponse.dt?.let {
        currTime = timeStampToHumanDate(it.toLong(), "hh:mm a")
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        WeatherScreenTitleSection(
            text = placeName, subText = date, fontSize = 40.sp, currTime = currTime
        )
        WeatherImage(icon = icon)
        TempAndDes(text = temp, subText = description, feelsLike = feelsLike,fontSize = 50.sp)
    }
    Spacer(modifier = Modifier.padding(16.dp))

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!showMore) {
            ShowButton(
                text = stringResource(R.string.show_more),
                icon = Icons.Default.ArrowDropDown,
                color = DeepBlue
            ) {
                showMore = true
            }
        }
        else {
            ShowButton(
                text = stringResource(R.string.show_less),
                icon = Icons.Filled.KeyboardArrowUp,
                color = DeepBlue
            ) {
                showMore = false
            }
        }

//        val navController = rememberNavController()
//
//        NavHost(
//            navController = navController,
//            startDestination = "weatherScreen"
//        ){
//            composable("weatherScreen"){
//                getFavScreen(navController = navController)
//            }
//            composable("favouriteScreen") {
//                FavouriteScreen()
//            }
//        }
//        @Composable
//        fun navigation() {
//
//        }
//        @Composable
//        fun favScreen(navController: NavController) {
//            // Clickable text that navigates to the DetailScreen
//            Text(
//                text = "Click me to go to the Detail Screen",
//                modifier = Modifier
//                    .padding(16.dp)
//                    .clickable {
//                        navController.navigate("favouriteScreen")
//                    }
//            )
//        }
//
//        @Composable
//        fun AppNavigation() {
//            // Set up the navigation graph
//            val navController = rememberNavController()
//
//            NavHost(
//                navController = navController,
//                startDestination = "mainScreen"
//            ) {
//                composable("mainScreen") {
//                    favScreen(navController = navController)
//                }
//                composable("favouriteScreen") {
//                    FavouriteScreen()
//                }
//            }
//        }
//        AppNavigation()
//        Column {
//            mainViewModel._favList.forEach {
//                Text(
//                    text = it,
//                    modifier = Modifier.padding(8.dp),
//                    color = Color.White,
//                    fontWeight = FontWeight.Bold
//                )
//            }
//        }

    }


    var wind = ""
    weatherResponse.wind.let {
        wind = if (it == null) LOADING else "${it.speed}"
    }

    var cloud = ""
    weatherResponse.clouds.let {
        cloud = if (it == null) LOADING else "${it.all}"
    }

    var snow = ""
    weatherResponse.snow.let {
        snow = if (it!!.d1h == null) NA else "${it.d1h}"
    }

    var humidity = ""
    weatherResponse.main?.let {
        humidity = "${it.humidity}%"
    }

    var pressure = ""
    weatherResponse.main?.let {
        pressure = "${it.pressure}\nhpa"
    }

    var rain = ""
    weatherResponse.rain.let {
        rain = if (it!!.d1h == null) NA else "${it.d1h}"
    }

    if (showMore) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                WeatherInfo(icon = painterResource(R.drawable.wind), text = "$wind\nm/s")
                WeatherInfo(icon = painterResource(R.drawable.cloud), text = "$cloud%")
                WeatherInfo(
                    icon = painterResource(R.drawable.snowflake),
                    text = if(snow == NA) snow else "$snow mm"
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                WeatherInfo(icon = painterResource(R.drawable.droplet), text = "$humidity")
                WeatherInfo(icon = painterResource(R.drawable.pressure), text = "$pressure")
                WeatherInfo(
                    icon = painterResource(R.drawable.cloud_rain),
                    text = if(rain == NA) rain else "$rain mm"
                )
            }
        }
    }

//    return Column(
//        modifier = Modifier
//            .fillMaxSize(),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(
//            text = weatherResponse.toString()
//        )
//    }
}

//@Composable
//fun getFavScreen(navController: NavController) {
//    Column {
//        Button(
//            onClick = {
//                      navController.navigate("favouriteScreen")
//            },
//            colors = ButtonDefaults.buttonColors(
//                containerColor = DeepBlue,
//                contentColor = Color.White
//            ),
//            shape = RoundedCornerShape(16.dp)
//        ) {
//            Text(text = "Favourites")
//        }
//    }
//}


@Composable
fun ShowButton(text: String, icon: ImageVector,color: Color, show: () -> Unit) {
    Button(
        onClick = {
            show()
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = color,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = text,
                textAlign = TextAlign.Justify,
                fontSize = 20.sp
            )
            Icon(
                imageVector = icon,
                contentDescription = text)
        }
    }
}

@Composable
fun WeatherInfo(icon: Painter, text: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = icon,
            contentDescription = null,
            Modifier
                .size(48.dp),
//                .background(Color.White),
            colorFilter = ColorFilter.tint(Color.White)
        )
        Spacer(modifier = Modifier.padding(4.dp))
        Text(
            text = text,
            fontSize = 20.sp,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}


@Composable
fun WeatherImage(
    icon: String
) {
    AsyncImage(
        model = buildIcon(icon),
        contentDescription = icon,
        modifier = Modifier
            .width(200.dp)
            .height(200.dp),
        contentScale = ContentScale.FillBounds,
        placeholder = painterResource(R.drawable.image_placeholder)
    )
}

@Composable
fun WeatherScreenTitleSection(text: String, subText: String, fontSize: TextUnit, currTime: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = fontSize
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Text(
            subText,
            color = Color.White,
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.padding(4.dp))
        Text(
            currTime,
            color = Color.White,
            fontSize = 20.sp
        )
    }
}

@Composable
fun TempAndDes(text: String, subText: String, fontSize: TextUnit, feelsLike: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            subText,
            color = Color.White,
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Text(
            text,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = fontSize
        )
        Spacer(modifier = Modifier.padding(4.dp))
        Text(
            "Feels like: $feelsLike",
            color = Color.White,
            fontSize = 20.sp
        )
    }
}

