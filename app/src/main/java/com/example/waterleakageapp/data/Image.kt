package com.example.waterleakageapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

class Image{
    var imageSrc :String? = null
    var imageTitle : String? = null
    var imageDesc : String? = null

    constructor(){}

    constructor(imageSrc: String?,imageTitle:String?,imageDesc:String?){
        this.imageSrc = imageSrc
        this.imageTitle = imageTitle
        this.imageDesc = imageDesc
    }

}

