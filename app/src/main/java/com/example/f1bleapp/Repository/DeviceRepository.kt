package com.example.f1bleapp.Repository

import androidx.lifecycle.LiveData
import com.example.f1bleapp.Data.Local.DeviceData

interface DeviceRepository {
    suspend fun insertDeviceDetail(deviceData: DeviceData)
    suspend fun deleteDeviceDetail(deviceData: DeviceData)
    fun getAllDevices(): LiveData<List<DeviceData>>
}