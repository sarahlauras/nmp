package com.mlbdev.mantapluarbiasa

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TeamBank (var idteam:Int, var idgame:Int, var nameteam:String, var imageURL:String, var is_shown:Int):
    Parcelable {
    override fun toString()=nameteam}
