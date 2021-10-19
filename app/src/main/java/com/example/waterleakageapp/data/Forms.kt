package com.example.waterleakageapp.data

import com.google.firebase.database.IgnoreExtraProperties


@IgnoreExtraProperties
data class Forms(
    var fId : String = "",
    var date: String = "",
    var category: String = "",
    var division: String = "",
    var district: String = "",
    var message: String = "",
    var status: String = ""
)

