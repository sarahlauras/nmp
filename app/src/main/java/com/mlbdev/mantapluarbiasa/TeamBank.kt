package com.mlbdev.mantapluarbiasa

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TeamBank (var idteam:Int, var idgame:Int, var nameteam:String, var namegame:String, var like:String, var image:Int, var description:String):
    Parcelable {
    override fun toString()=nameteam}
