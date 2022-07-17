package com.example.f1bleapp.Data.Local

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CsvData(
    var timeStamp: String,
    var message1: String,
    var message2: String,
    var message3: String,
    var message4: String,
    var message5: String,
    var message6: String,
    var message7: String,
    var message8: String,
    var message9: String,
    var message10: String,
    var message11: String,
    var message12: String
) : Parcelable
