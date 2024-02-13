package com.example.weatherapplication.data.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

 class FavViewModel(application: Application) : AndroidViewModel(application) {
    private  val database = Room.databaseBuilder(
        application.applicationContext,
        FavDatabase::class.java,
        "favourite_database"
    ).build()
//    val favCities  = getFavouriteCities()
//    private var favCityList: LiveData<List<CityEntity>> = MutableLiveData<List<CityEntity>>().apply {
//        value = emptyList()
//    }
////    val favCities: StateFlow<List<CityEntity>> = _favCities
//
//    private fun getFavouriteCities(): LiveData<List<CityEntity>> {
//        viewModelScope.launch {
//            favCityList =
//                withContext(Dispatchers.IO) {
//                 database.cityDao().getAllCities().asLiveData()
//            }
//        }
//        return favCityList
//    }

     val favCities = Dispatchers.IO.run {
         database.cityDao().getAllCities().asLiveData()
     }

    fun addCity(city: CityEntity){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                database.cityDao().addCity(city)
            }
        }
    }

    fun removeCity(city: CityEntity) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                database.cityDao().deleteCity(city)
            }
        }
    }

    suspend fun isCityInFavourites(city: String): Boolean {
        return withContext(Dispatchers.IO) {
            database.cityDao().isCityInFavourites(city)
        }
    }
}