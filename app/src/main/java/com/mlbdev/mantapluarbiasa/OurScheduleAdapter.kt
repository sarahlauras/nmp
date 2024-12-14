package com.mlbdev.mantapluarbiasa

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mlbdev.mantapluarbiasa.databinding.ScheduleCardBinding
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class OurScheduleAdapter(private val scheduleList:ArrayList<OurScheduleBank>): RecyclerView.Adapter<OurScheduleAdapter.OurScheduleViewHolder>() {
    private var upcomingScheduleList = scheduleList.filter { schedule ->
//        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
//        val eventDate = dateFormat.parse(schedule.date)
//        eventDate?.after(Date()) == true
        true
    }


    class OurScheduleViewHolder(val binding:ScheduleCardBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OurScheduleViewHolder {
        val binding = ScheduleCardBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return OurScheduleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OurScheduleViewHolder, position: Int) {
        val schedule = upcomingScheduleList[position]

        holder.binding.txtTgl.text = schedule.date
        holder.binding.txtNamaSchedule.text = schedule.nama_event
        holder.binding.txtGame.text = schedule.nama_game
        holder.binding.txtTeam.text = schedule.nama_team

        holder.itemView.setOnClickListener {
            val context = it.context
            val intent = Intent(context, SchedulePageDetail::class.java).apply {
                putExtra("SCHEDULE_NAME", schedule.nama_event)
                putExtra("GAME_NAME", schedule.nama_game)
                putExtra("TEAM_NAME", schedule.nama_team)
                putExtra("SCHEDULE_DATE", schedule.date)
                putExtra("IDEVENT", schedule.idevent)
                putExtra("IDTEAM", schedule.idteam)
            }
            context.startActivity(intent)
        }
        Log.d("OurScheduleAdapter", "Upcoming events count: ${upcomingScheduleList.size}")
        Log.d("OurScheduleAdapter", "Sending data to SchedulePageDetail: ${schedule.nama_event}, ${schedule.nama_game}, ${schedule.nama_team}, Index: ${schedule.idevent}")
    }

    override fun getItemCount(): Int {
        return upcomingScheduleList.size
    }
}