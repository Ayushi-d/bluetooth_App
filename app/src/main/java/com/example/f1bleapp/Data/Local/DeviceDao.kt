package com.example.f1bleapp.Data.Local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DeviceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDeviceDetail(deviceData: DeviceData)

    @Delete
    suspend fun deleteDeviceDetail(deviceData: DeviceData)

    @Query("SELECT * FROM device_detail")
    fun getDevice(): LiveData<List<DeviceData>>

}