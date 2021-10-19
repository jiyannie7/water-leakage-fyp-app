package com.example.waterleakageapp.utils

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

object Time {

    fun timeStamp():String{
        val timeStamp = Timestamp(System.currentTimeMillis())
        val sdf = SimpleDateFormat("HH:mm",Locale.getDefault())
        val time = sdf.format(Date(timeStamp.time))

        return time.toString()
    }
}