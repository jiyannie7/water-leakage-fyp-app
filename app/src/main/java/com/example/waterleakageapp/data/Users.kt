package com.example.waterleakageapp.data

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Users(

    var name:String = "",
    var email:String ="",
    var contact: String = "")



