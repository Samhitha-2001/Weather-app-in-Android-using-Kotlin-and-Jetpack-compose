package com.example.weatherapplication.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CityEntity::class], version = 1, exportSchema = false)
abstract class FavDatabase : RoomDatabase() {
    abstract fun cityDao(): CityDao
}