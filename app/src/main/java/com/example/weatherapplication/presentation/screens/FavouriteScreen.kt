package com.example.weatherapplication.presentation.screens

import android.app.Application
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.asFlow
import androidx.lifecycle.map
import com.example.weatherapplication.presentation.ui.theme.DeepBlue
import com.example.weatherapplication.data.room.FavViewModel
import com.example.weatherapplication.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

//private var mainViewModel: MainViewModel = MainViewModel()
private var favCityPressed by mutableStateOf(false)
//private var favViewModel: FavViewModel = FavViewModel(application = Application())

@Composable
fun FavouriteScreen(mainViewModel: MainViewModel) {

    val favCities by mainViewModel.favViewModel.favCities.observeAsState(initial = emptyList())

    Column {
        for (city in favCities) Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = DeepBlue
            )
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = city.name,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            mainViewModel.showFavScreen = false
                            mainViewModel.locInput = true
                            mainViewModel.location = city.name
                        }
                )
                Button(
                    onClick = {
                        mainViewModel.favViewModel.removeCity(city)
//                            mainViewModel.removeFavCity(city.name)
                        mainViewModel.alertReturn = ""
                    },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                        containerColor = DeepBlue
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete"
                    )
                }
            }
        }
    }
//
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        favCities.map { city->
//            Card(
//                modifier = Modifier
//                    .padding(16.dp)
//                    .fillMaxWidth(),
//                shape = RoundedCornerShape(8.dp),
//                colors = CardDefaults.cardColors(
//                    containerColor = DeepBlue
//                )
//            ) {
//                Row(
//                    modifier = Modifier
//                        .padding(16.dp)
//                        .fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//                    Text(
//                        text = city,
//                        fontWeight = FontWeight.Bold,
//                        color = Color.White,
//                        modifier = Modifier
//                            .padding(8.dp)
//                            .clickable {
//                                mainViewModel.showFavScreen = false
//                                mainViewModel.location = city
//                            }
//                    )
//                    Button(
//                        onClick = {
//                            mainViewModel.removeFavCity(city)
//                            mainViewModel.alertReturn = "Dismiss"
//                        },
//                        colors = ButtonDefaults.buttonColors(
//                            contentColor = Color.White,
//                            containerColor = DeepBlue
//                        )
//                        ) {
//                        Icon(
//                            imageVector = Icons.Default.Delete,
//                            contentDescription = "Delete"
//                        )
//                    }
//                }
//            }
//        }
//    }

    if (!mainViewModel.showFavScreen) {
        MainScreen(
            weatherResponse = mainViewModel.weatherResponse,
            forecastResponse = mainViewModel.forecastResponse,
            mainViewModel = mainViewModel
        )
    }


}


//@Preview
//@Composable
//private  fun favPreview() {
//    FavouriteScreen()
//}