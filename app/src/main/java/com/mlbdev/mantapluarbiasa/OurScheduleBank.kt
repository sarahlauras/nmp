package com.mlbdev.mantapluarbiasa

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OurScheduleBank(var date:String, var nama_schedule:String,
                           var nama_game:String, var nama_team:String) : Parcelable{
                               override fun toString()=nama_schedule
                           }