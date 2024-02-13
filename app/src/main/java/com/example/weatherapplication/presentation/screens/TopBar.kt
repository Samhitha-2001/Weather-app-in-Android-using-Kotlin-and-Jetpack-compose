package com.example.weatherapplication.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapplication.R
import com.example.weatherapplication.presentation.ui.theme.DarkBlue
import com.example.weatherapplication.presentation.ui.theme.DeepBlue
import com.example.weatherapplication.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

//private var mainViewModel: MainViewModel = MainViewModel()

var isSheetOpen by mutableStateOf(false)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(mainViewModel: MainViewModel){

    val coroutineScope = rememberCoroutineScope()

    Row(
        horizontalArrangement = if (mainViewModel.isTextFieldVisible) Arrangement.SpaceEvenly else Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 16.dp, bottom = 16.dp, end = 20.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            FloatingActionButton(
                onClick = {
                    isSheetOpen = !isSheetOpen
                },
                containerColor = DeepBlue,
                contentColor = Color.White,
                shape = RoundedCornerShape(18.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu",
                    Modifier.size(42.dp)
                )
            }
            if (isSheetOpen) {
                TopSheet(mainViewModel)
//                SideBar(mainViewModel)
            }
            if (mainViewModel.isTextFieldVisible) {
                TextField(
                    value = mainViewModel.location,
                    onValueChange = {
                        mainViewModel.location = it
                        mainViewModel.locInput = true
                        mainViewModel.alertReturn = ""
                        coroutineScope.launch {
                            delay(10000)
                            mainViewModel.isTextFieldVisible = false
                        }
                    },
                    label = {
                        Text(
                            "Enter City Name",
                            color = Color.White,
//                                        fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = DarkBlue,
                        textColor = Color.White,
                    ),
                    textStyle = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        lineHeight = 80.sp,

                        ),
                    singleLine = true
                )
            }
        }
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            FloatingActionButton(
                onClick = {
                    mainViewModel.isTextFieldVisible = false
                    mainViewModel.isSearchIconVisible = true
                    mainViewModel.location = ""
                    mainViewModel.alertReturn = ""
                },
                containerColor = DeepBlue,
                contentColor = Color.White,
                shape = RoundedCornerShape(18.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "location",
                    Modifier.size(42.dp)
                )
            }
        }
    }
}

@Composable
fun TopSheet(mainViewModel: MainViewModel) {
    PersistentTopSheet(
        isOpen = isSheetOpen,
        onDismiss = {
            isSheetOpen = false
        }
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Bottom
        ) {
            Button(
                onClick = {
                    mainViewModel.isTextFieldVisible = true
                    isSheetOpen = false
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
                    isSheetOpen = false
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
                Spacer(modifier = Modifier.padding(8.dp))
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = stringResource(R.string.favourite_cities)
                )
            }
        }
    }

}


