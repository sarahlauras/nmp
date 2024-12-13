package com.mlbdev.mantapluarbiasa

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ScheduleDetailBank (var nama_event: String,
                               var nama_game: String,
                               var location: String,
                               var time: String,
                               var nama_team: String,
                               var description: String,
                               var image_url: String):
    Parcelable {
    override fun toString()=nama_event}