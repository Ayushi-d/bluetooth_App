package com.example.f1bleapp

import androidx.lifecycle.SavedStateHandle
import com.example.f1bleapp.Data.Local.CsvData
import com.example.f1bleapp.Data.Local.DeviceDao
import com.example.f1bleapp.Data.Local.DeviceData
import javax.inject.Inject

class csvViewModel @Inject constructor(
    private val dataDao : DeviceDao,
    private val state: SavedStateHandle
) {
    val csvData = state.get<CsvData>("csvData")
}