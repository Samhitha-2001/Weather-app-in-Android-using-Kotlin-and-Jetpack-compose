package com.example.weatherapplication.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCity(city: CityEntity)

    @Delete
    suspend fun deleteCity(city: CityEntity)

    @Query("SELECT * FROM cities")
    fun getAllCities():
            Flow<List<CityEntity>>

    @Query("SELECT EXISTS (SELECT 1 FROM cities WHERE name = :city) ")
    fun isCityInFavourites(city: String) : Boolean
}