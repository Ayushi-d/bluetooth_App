package com.example.f1bleapp.Data.Local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [DeviceData::class], version = 1, exportSchema = false
)
abstract class DeviceDetailDatabase : RoomDatabase() {

    abstract fun deviceDao(): DeviceDao
}