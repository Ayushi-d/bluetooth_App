package com.example.f1bleapp.UI

import android.net.Uri
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.f1bleapp.Data.Local.DeviceDao
import com.example.f1bleapp.Data.Local.DeviceData
import com.example.f1bleapp.Repository.DeviceRepository
import kotlinx.coroutines.launch

class AppViewModel @ViewModelInject constructor(
    private val repository: DeviceRepository,
    private val dao: DeviceDao
) : ViewModel() {

    val device = MutableLiveData<List<DeviceData>>()

    fun insertDeviceDetail(deviceData: DeviceData) = viewModelScope.launch {
        dao.insertDeviceDetail(deviceData)
        repository.insertDeviceDetail(deviceData)
    }

    fun deleteDeviceDetail(deviceData: DeviceData) = viewModelScope.launch {
        dao.deleteDeviceDetail(deviceData)
    }

    val getDevice = repository.getAllDevices()

    private val _curCsvData = MutableLiveData<Uri>()
    val curCsvData: LiveData<Uri> = _curCsvData


    fun setCsvUri(uri: Uri) {
        _curCsvData.postValue(uri)
    }
//    fun readData(uri: Uri, contentResolver: ContentResolver): List<String> {
//        val csvFile = contentResolver.openInputStream(uri)
//        val isr = InputStreamReader(csvFile)
//        return BufferedReader(isr).readLines()
//    }
}

