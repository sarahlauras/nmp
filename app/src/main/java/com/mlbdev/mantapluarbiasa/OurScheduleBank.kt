package com.mlbdev.mantapluarbiasa

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class OurScheduleBank(var idevent: String, var date:String,
                           var nama_event:String, var nama_game:String, var nama_team:String, var idteam:Int) : Parcelable{
                               override fun toString()=nama_event
                           }