package com.example.f1bleapp.Data.Local

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "device_detail")
@Parcelize
data class DeviceData(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    var deviceName: String?,
    var deviceMacId: String?
) : Parcelable

