package com.mlbdev.mantapluarbiasa

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameBank(var title:String, var description:String, var imageId:Int) :
    Parcelable{
    override fun toString()=title
}