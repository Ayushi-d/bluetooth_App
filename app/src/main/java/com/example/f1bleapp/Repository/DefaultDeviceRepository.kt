package com.example.f1bleapp.Repository

import android.content.ContentResolver
import android.net.Uri
import androidx.lifecycle.LiveData
import com.example.f1bleapp.Data.Local.CsvData
import com.example.f1bleapp.Data.Local.DeviceDao
import com.example.f1bleapp.Data.Local.DeviceData
import javax.inject.Inject

class DefaultDeviceRepository @Inject constructor(
    private val deviceDao: DeviceDao
) : DeviceRepository {
    override suspend fun insertDeviceDetail(deviceData: DeviceData) {
        deviceDao.insertDeviceDetail(deviceData)
    }

    override suspend fun deleteDeviceDetail(deviceData: DeviceData) {
        deviceDao.deleteDeviceDetail(deviceData)
    }

    override fun getAllDevices(): LiveData<List<DeviceData>> {
        return deviceDao.getDevice()
    }
}