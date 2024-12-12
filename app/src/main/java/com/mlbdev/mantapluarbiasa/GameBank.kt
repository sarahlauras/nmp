package com.mlbdev.mantapluarbiasa

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameBank(var name:String, var description:String, var imageId:String) :
    Parcelable{
    override fun toString()=name
}