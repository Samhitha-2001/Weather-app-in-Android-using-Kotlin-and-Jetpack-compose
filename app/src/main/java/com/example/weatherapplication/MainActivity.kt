package com.example.weatherapplication

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapplication.common.constants.Constant.Companion.permissions
import com.example.weatherapplication.common.model.LatLong
import com.example.weatherapplication.presentation.screens.ErrorScreen
import com.example.weatherapplication.presentation.screens.LoadingScreen
import com.example.weatherapplication.presentation.screens.MainScreen
import com.example.weatherapplication.presentation.screens.alertScreen
import com.example.weatherapplication.presentation.ui.theme.DarkBlue
import com.example.weatherapplication.presentation.ui.theme.WeatherApplicationTheme
import com.example.weatherapplication.data.room.CityEntity
import com.example.weatherapplication.data.room.FavViewModel
import com.example.weatherapplication.presentation.screens.HandleLocationPermissions
import com.example.weatherapplication.presentation.viewmodel.MainViewModel
import com.example.weatherapplication.presentation.viewmodel.State
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

//private var favViewModel: FavViewModel = FavViewModel(application = Application())

class MainActivity : ComponentActivity() {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private var locationRequired: Boolean = false
    private lateinit var mainViewModel: MainViewModel
    private lateinit var favViewModel: FavViewModel

    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>


    override fun onResume() {
        super.onResume()
        if (locationRequired) startLocationUpdate()
    }

    override fun onPause() {
        super.onPause()
        locationCallback.let {
            fusedLocationProviderClient.removeLocationUpdates(it)
        }
    }

//    @SuppressLint("MissingPermission")
    private fun startLocationUpdate() {
        locationCallback.let {
            val locationRequest = LocationRequest.Builder(
                Priority.PRIORITY_HIGH_ACCURACY, 100
            )
                .setWaitForAccurateLocation(false)
                .setMinUpdateIntervalMillis(3000)
                .setMaxUpdateDelayMillis(100)
                .build()


            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {

            }
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                it,
                Looper.getMainLooper()
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLocationClient()
        initViewModel()
        initFavViewModel()
        setContent {

            var currentLocation by remember {
                mutableStateOf(LatLong(0.0, 0.0))
            }

            locationCallback = object : LocationCallback() {
                override fun onLocationResult(p0: LocationResult) {
                    super.onLocationResult(p0)
                    for (location in p0.locations) {
                        currentLocation = LatLong(
                            location.latitude,
                            location.longitude
                        )
                    }

                    fetchWeatherInformation(mainViewModel, currentLocation)
                }
            }

            WeatherApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LocationScreen(this@MainActivity, currentLocation)
                }
            }
        }
    }

    private fun initFavViewModel() {
        favViewModel = ViewModelProvider(this)[FavViewModel::class.java]
    }

    private fun fetchWeatherInformation(mainViewModel: MainViewModel, currentLocation: LatLong) {
        mainViewModel.state = State.LOADING
        mainViewModel.getWeatherByLocation(currentLocation)
        mainViewModel.getForecastByLocation(currentLocation)
        mainViewModel.state = State.SUCCESS

    }

    private fun initViewModel() {
        mainViewModel = ViewModelProvider(this@MainActivity)[MainViewModel::class.java]
    }

    private fun initLocationClient() {
        fusedLocationProviderClient = LocationServices
            .getFusedLocationProviderClient(this)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun LocationScreen(context: Context, currentLocation: LatLong) {

        HandleLocationPermissions(
            context = this@MainActivity,
            permissions = permissions,
            onPermissionGranted = { startLocationUpdate() },
            onPermissionDenied = {
                Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()

            }
            )

        val systemUiController = rememberSystemUiController()

        DisposableEffect(key1 = true, effect = {
            systemUiController.isSystemBarsVisible = false
            onDispose {
                systemUiController.isSystemBarsVisible = true
            }
        })

//        LaunchedEffect(key1 = currentLocation, block = {
//            if (permissions.all {
//                    ContextCompat.checkSelfPermission(
//                        context,
//                        it
//                    ) == PackageManager.PERMISSION_GRANTED
//                }) {
//                startLocationUpdate()
//            } else {
//                launcherMultiplePermissions.launch(permissions)
//            }
//        })


//        var isSearchIconVisible by remember { mutableStateOf(true) }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkBlue)
                .padding(16.dp)
        ) {
//            Image(
//                painter = painterResource(R.drawable.backgrnd7),
//                contentDescription = stringResource(R.string.background),
//                modifier = Modifier
//                    .fillMaxSize(),
//                contentScale = ContentScale.Crop
//            )
            Column {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    var name  = ""
                    mainViewModel.weatherResponse.name?.let {
                        name = it
                    }

                    when (mainViewModel.state) {
                        State.LOADING -> {
                            LoadingScreen()
                        }
                        State.FAILED -> {
                            ErrorScreen(mainViewModel.errorMessage)
                        }
                        else -> {
                            MainScreen(
                                weatherResponse = mainViewModel.weatherResponse,
                                forecastResponse = mainViewModel.forecastResponse,
                                mainViewModel = mainViewModel
                            )
                            if (mainViewModel.showAlert) {
                                alertScreen(
                                    onDismissRequest = {
                                        mainViewModel.showAlert = false
                                        mainViewModel.alertReturn = "Dismiss"
                                    },
                                    onConfirmation = {
                                        mainViewModel.showAlert = false
                                        if (mainViewModel.location == ""){
                                            mainViewModel.favViewModel.addCity(CityEntity(name = name))
//                                            mainViewModel.addFavCity(name)
                                            mainViewModel.alertReturn = ""
                                        }
                                        else {
//                                            mainViewModel.addFavCity(mainViewModel.location)
                                            mainViewModel.alertReturn = ""
                                            mainViewModel.favViewModel.addCity(CityEntity(name = mainViewModel.location))
                                        }
                                    },
                                    dialogTitle = "Add to favourites?",
                                    dialogText = "Do you want to add ${if (mainViewModel.location == ""){ name } else mainViewModel.location} to favourite list?",
                                    icon = Icons.Default.Star
                                )
                            }
                            if (mainViewModel.showErrorAlert) {
                                AlertDialog(
                                    onDismissRequest = {
                                                   mainViewModel.showErrorAlert = false
                                    },
                                    buttons = {
                                        TextButton(onClick = {
                                            mainViewModel.showErrorAlert = false
                                            mainViewModel.location = ""
                                        }) {
                                            Text(text = "Okay")
                                        }
                                    },
                                    title = {
                                        Text(
                                            text = "Invalid city name",
                                            fontWeight = FontWeight.Bold
                                        )
                                    },
                                    text = {
                                        Text(
                                            text = "Please enter a valid city name"
                                        )
                                    }
                                    )
                            }
                        }
                    }

                }
            }

        }
    }




}




