package com.example.weatherapplication.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BackdropScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.weatherapplication.R
import com.example.weatherapplication.presentation.ui.theme.DeepBlue
import com.example.weatherapplication.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

//private var mainViewModel : MainViewModel = MainViewModel()

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun SideBar(mainViewModel: MainViewModel) {

    val drawerState =  rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Menu",
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        coroutineScope.launch {
                            drawerState.open()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Menu, 
                            contentDescription = "Menu"
                        )
                    }
                }
            )
        },
        drawerContent = {
            DrawerContent(drawerState, mainViewModel)
        },
        content = {
            MainScreen(
                weatherResponse = mainViewModel.weatherResponse,
                forecastResponse = mainViewModel.forecastResponse,
                mainViewModel = mainViewModel)
        }
    ) 
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerContent(drawerState: DrawerState, mainViewModel: MainViewModel) {

    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                coroutineScope.launch {
                    drawerState.close()
                }
                mainViewModel.isTextFieldVisible = true
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = DeepBlue,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(18.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Search")
                Spacer(modifier = Modifier.padding(9.dp))
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
//                        Modifier.size(42.dp)
                )
            }
        }
        Spacer(modifier = Modifier.padding(16.dp))
        Button(
            onClick = {
                mainViewModel.showFavScreen = true
                coroutineScope.launch {
                    drawerState.close()
                }
                mainViewModel.isTextFieldVisible = false
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = DeepBlue,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = stringResource(R.string.favourite_cities)
            )
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = stringResource(R.string.favourite_cities)
            )
        }
    }
}