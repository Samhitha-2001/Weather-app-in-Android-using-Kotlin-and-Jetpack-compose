package com.example.weatherapplication.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapplication.R
import com.example.weatherapplication.data.parsers.forecast.ForecastResult
import com.example.weatherapplication.data.parsers.weather.WeatherResult
import com.example.weatherapplication.presentation.ui.theme.DeepBlue
import com.example.weatherapplication.presentation.viewmodel.MainViewModel

//private var mainViewModel: MainViewModel = MainViewModel()

@Composable
fun MainScreen(weatherResponse: WeatherResult, forecastResponse: ForecastResult, mainViewModel: MainViewModel) {
    Column {
        if (!mainViewModel.showFavScreen) {
            Box {
                Spacer(modifier = Modifier.padding(50.dp))
                Column(
                    modifier = Modifier.padding(top = 28.dp)
                ) {
                    Spacer(modifier = Modifier.padding(24.dp))
                    WeatherScreen(weatherResponse)
                    Spacer(modifier = Modifier.padding(8.dp))
                    ForecastScreen(forecastResponse)
                }
                Spacer(modifier = Modifier.padding(24.dp))
                TopBar(mainViewModel)
            }

        }
        else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            mainViewModel.showFavScreen = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = DeepBlue,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(18.dp),
                        modifier = Modifier.size(80.dp)
                        ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                    Text(
                        text = "Favourite cities",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(28.dp),
                        fontSize = 25.sp
                    )
                }
                Row {
                    Spacer(modifier = Modifier.padding(16.dp))
                }
                FavouriteScreen(mainViewModel)
            }
        }
    }

}



